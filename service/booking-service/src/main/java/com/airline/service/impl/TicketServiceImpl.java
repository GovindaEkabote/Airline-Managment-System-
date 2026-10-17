package com.airline.service.impl;

import com.airline.enums.TicketStatus;
import com.airline.model.Booking;
import com.airline.model.Passenger;
import com.airline.model.Ticket;
import com.airline.repository.TicketRepository;
import com.airline.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> generateTicketsForBooking(Booking booking) {

        if (booking == null || booking.getId() == null) {
            throw new IllegalArgumentException(
                    "A persisted booking is required"
            );
        }

        if (booking.getPassengers() == null
                || booking.getPassengers().isEmpty()) {
            throw new IllegalArgumentException(
                    "Booking must contain at least one passenger"
            );
        }
        // Load existing tickets to make repeated calls idempotent.
        List<Ticket> existingTickets =
                ticketRepository.findByBookingId(booking.getId());

        List<Ticket> result = new ArrayList<>(existingTickets);
        Set<Long> ticketedPassengerIds = new HashSet<>();

        for (Ticket existingTicket : existingTickets) {
            if (existingTicket.getPassenger() != null
                    && existingTicket.getPassenger().getId() != null) {
                ticketedPassengerIds.add(
                        existingTicket.getPassenger().getId()
                );
            }
        }

        List<Ticket> newTickets = new ArrayList<>();

        for (Passenger passenger : booking.getPassengers()) {

            if (passenger.getId() == null) {
                throw new IllegalArgumentException(
                        "All passengers must be persisted before ticket issuance"
                );
            }

            // Do not issue a second ticket for the same passenger.
            if (ticketedPassengerIds.contains(passenger.getId())) {
                continue;
            }

            Ticket ticket = Ticket.builder()
                    .ticketNumber(generateUniqueTicketNumber())
                    .status(TicketStatus.ISSUED)
                    .issuedAt(Instant.now())
                    .booking(booking)
                    .passenger(passenger)
                    .build();

            booking.addTicket(ticket);
            newTickets.add(ticket);

            ticketedPassengerIds.add(passenger.getId());
        }

        if (!newTickets.isEmpty()) {
            List<Ticket> savedTickets =
                    ticketRepository.saveAll(newTickets);

            result.addAll(savedTickets);
        }

        return result;
    }

    private  String generateUniqueTicketNumber(){
        String ticketNumber;

        do {
            String datePart = LocalDate.now().toString();
            String randomPart = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();

            ticketNumber = "AIR-" + datePart + "-" + randomPart;

        } while (ticketRepository.existsByTicketNumber(ticketNumber));

        return ticketNumber;
    }
}
