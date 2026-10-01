package com.airline.service.impl;

import com.airline.enums.FlightStatus;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.FlightScheduleMapper;
import com.airline.model.Flight;
import com.airline.model.FlightSchedule;
import com.airline.playload.request.FlightInstanceRequest;
import com.airline.playload.request.FlightScheduleRequest;
import com.airline.playload.response.*;
import com.airline.repository.FlightRepository;
import com.airline.repository.FlightScheduleRepository;
import com.airline.service.FlightInstanceService;
import com.airline.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId,
                                                       FlightScheduleRequest request) {
        Flight flight = flightRepository.findById(airlineId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "airlineId not found",
                        "id",
                        airlineId));

        if (request.getEndDate().isBefore(request.getStartDate())){
            throw new IllegalArgumentException("End date must be after start date");
        }

        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);
        FlightSchedule savedSchedule = flightScheduleRepository.save(flightSchedule);

        List<DayOfWeek> operatingDays = savedSchedule.getOperatingDays();
        LocalDate startDate = savedSchedule.getStartDate();
        LocalDate endDate = savedSchedule.getEndDate();

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest
                .builder()
                .scheduleId(savedSchedule.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();

        for (LocalDate date=startDate ; !date.isAfter(endDate) ; date=date.plusDays(1)){
            if (operatingDays.contains(date.getDayOfWeek())){
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date,savedSchedule.getDepartureTime())
                );

                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date, savedSchedule.getArrivalTime())
                );

                flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest);
            }
        }


        return convertToFlightScheduleResponse(savedSchedule);
    }


    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) {

        FlightSchedule flightSchedule= flightScheduleRepository.findById(id).orElseThrow(
                        () ->new ResourceNotFoundException( "Flight not found",
                        "id",
                        id
                ));

        return convertToFlightScheduleResponse(flightSchedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightSchedulesByAirline(Long airlineId) {
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findByAirlineId(airlineId);
        return flightSchedules.stream()
                .map(this::convertToFlightScheduleResponse)
                .collect(Collectors.toList()
                );
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(
            Long scheduleId,
            FlightScheduleRequest flightSchedule) {
        FlightSchedule flightSchedule1 =
                flightScheduleRepository.findById(scheduleId).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Flight not found",
                                "id",
                                scheduleId
                        )
                );
        if (flightSchedule.getEndDate().isBefore(flightSchedule.getStartDate())){
            throw new IllegalArgumentException("End date must be after start date");
        }
        FlightScheduleMapper.updateEntity(flightSchedule1, flightSchedule);
        FlightSchedule schedule = flightScheduleRepository.save(flightSchedule1);
        return convertToFlightScheduleResponse(schedule);
    }

    @Override
    public void deleteFlightSchedule(Long id) {
        FlightSchedule schedule =
                flightScheduleRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Flight Not Found to Delete",
                                "id",
                                id
                        )
                );
        flightScheduleRepository.delete(schedule);
    }

    @Override
    public Page<FlightScheduleResponse> getAllFlightSchedules(
            Pageable pageable) {
        return flightScheduleRepository
                .findAll(pageable)
                .map(this::convertToFlightScheduleResponse);
    }

    @Override
    public Page<FlightScheduleResponse> getSchedulesByFlight(Long flightId, Pageable pageable) {
        return flightScheduleRepository
                .findByFlightId(flightId, pageable)
                .map(this::convertToFlightScheduleResponse);
    }

    @Override
    public Page<FlightScheduleResponse> getSchedulesByDepartureAirport(
            Long airportId, Pageable pageable) {
        return flightScheduleRepository
                .findByDepartureAirportId(airportId, pageable)
                .map(this::convertToFlightScheduleResponse);
    }

    @Override
    public Page<FlightScheduleResponse> getSchedulesByArrivalAirport(Long airportId, Pageable pageable) {
        return flightScheduleRepository
                .findByArrivalAirportId(airportId, pageable)
                .map(this::convertToFlightScheduleResponse);
    }

    @Override
    public Page<FlightScheduleResponse> getSchedulesByDay(DayOfWeek dayOfWeek, Pageable pageable) {
        return flightScheduleRepository
                .findByOperatingDaysContaining(dayOfWeek, pageable)
                .map(this::convertToFlightScheduleResponse);
    }

    private FlightScheduleResponse convertToFlightScheduleResponse(
            FlightSchedule schedule) {
//        todo service to service communication...
        AirportResponse departureAirport = AirportResponse.builder()
                .id(schedule.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(schedule.getArrivalAirportId())
                .build();
        FlightScheduleResponse flightResponse =
                FlightScheduleMapper.toResponse(
                        schedule,
                        departureAirport,
                        arrivalAirport
                );
        return FlightScheduleMapper.toResponse(
                schedule,
                departureAirport,
                arrivalAirport
        );
    }
}
