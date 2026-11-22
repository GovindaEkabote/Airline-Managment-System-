package com.airline.service;


import com.airline.event.BookingConfirmationEvent;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${notification.from-email}")
    private String fromEmail;

    @Value("${notification.from-name}")
    private String fromName;

    private static  final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd MM yyyy", Locale.ENGLISH);
    private static  final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
            .ofPattern("HH:mm", Locale.ENGLISH);
    private static  final DateTimeFormatter DT_FORMATTER = DateTimeFormatter
            .ofPattern("dd MM yyyy, HH:mm", Locale.ENGLISH);


    public void sendBookingConfirmation(BookingConfirmationEvent bookingConfirmationEvent) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                message, true, "UTF-8"
        );

        mimeMessageHelper.setFrom(fromEmail, fromName);
        mimeMessageHelper.setTo(bookingConfirmationEvent.getContactEmail());
        mimeMessageHelper.setSubject(buildSubject(bookingConfirmationEvent));
        mimeMessageHelper.setText(buildHtmlBody(bookingConfirmationEvent), true);
    }

    private String buildHtmlBody(BookingConfirmationEvent bookingConfirmationEvent) {
        Context ctx = new Context();
        ctx.setVariable("booking", bookingConfirmationEvent);
        ctx.setVariable("passengerCount",
                bookingConfirmationEvent.getPassengers() != null
                        ? bookingConfirmationEvent.getPassengers().size()
                        : 1
        );
        ctx.setVariable("depDate",
                bookingConfirmationEvent.getDepartureDateTime() != null
                        ? bookingConfirmationEvent.getArrivalDateTime()
                        .format(DATE_TIME_FORMATTER)
                        : "N/A"
                );
        ctx.setVariable("depTime",
                bookingConfirmationEvent.getDepartureDateTime() != null
                        ? bookingConfirmationEvent.getArrivalDateTime()
                        .format(TIME_FORMATTER)
                        : "N/A"
        );
        ctx.setVariable("arrDate",
                bookingConfirmationEvent.getDepartureDateTime() != null
                        ? bookingConfirmationEvent.getArrivalDateTime()
                        .format(DATE_TIME_FORMATTER)
                        : "N/A"
        );
        ctx.setVariable("paidAt",
                bookingConfirmationEvent.getPaidAt() != null
                        ? bookingConfirmationEvent.getPaidAt()
                        .format(DATE_TIME_FORMATTER)
                        : "N/A"
        );
        ctx.setVariable("bookingDate",
                bookingConfirmationEvent.getBookingDate() != null
                        ? bookingConfirmationEvent.getBookingDate()
                        .format(DATE_TIME_FORMATTER)
                        : "N/A"
        );
        BigDecimal base = orZero(bookingConfirmationEvent.getBaseFare());
        BigDecimal taxes = orZero(bookingConfirmationEvent.getTaxesAndFees());
        BigDecimal seats = orZero(bookingConfirmationEvent.getSeatFees());
        BigDecimal ancillary = orZero(bookingConfirmationEvent.getAncillaryFees());
        BigDecimal meals = orZero(bookingConfirmationEvent.getMealFees());
        BigDecimal total = orZero(bookingConfirmationEvent.getTotalAmount());
        ctx.setVariable("baseFareTotal", fmt(base));
        ctx.setVariable("taxes", fmt(taxes));
        ctx.setVariable("seats", fmt(seats));
        ctx.setVariable("ancillary", fmt(ancillary));
        ctx.setVariable("meals", fmt(meals));
        ctx.setVariable("total", fmt(total));

        ctx.setVariable("hasBaggage",
                bookingConfirmationEvent.getCheckinBaggagePieces() != null || bookingConfirmationEvent.getCabinBaggagePieces() != null);
        ctx.setVariable("checkinBaggage", baggageLabel(
                bookingConfirmationEvent.getCheckinBaggagePieces(), bookingConfirmationEvent.getCheckinBaggageWeightPerPieces()
        ));
        ctx.setVariable("checkinBaggage", baggageLabel(
                bookingConfirmationEvent.getCheckinBaggagePieces(), bookingConfirmationEvent.getCabinBaggageWeightPerPieces()
        ));

        ctx.setVariable("hasBaggage",
                bookingConfirmationEvent.getCheckinBaggagePieces() != null || bookingConfirmationEvent.getCabinBaggagePieces() != null);

        ctx.setVariable("cabinClassDisplay",
                cabinDisplayName(bookingConfirmationEvent.getCabinClass())
        );

        return templateEngine.process("email/booking-confirmation",ctx);
    }

    private static String cabinDisplayName(String cabinClass){
        if (cabinClass == null){
            return "Economy";
        }

        return switch (cabinClass){
            case "ECONOMY" -> "Economy";
            case "PREMIUM_ECONOMY" -> "Premium Economy";
            case "BUSINESS" -> "Business";
            case "FIRST" -> "First Class";
            default ->  cabinClass;

        };
    }

    private Object baggageLabel(Integer pieces, Double weightPer){
        if (pieces == null && weightPer == null){
            return "Not included";
        }
        if (pieces != null && weightPer != null){
            return pieces + " \u00d7" + weightPer.intValue() + "kg";
        }
        if (pieces != null){
            return pieces + "pieces(s)";
        }
        return weightPer.intValue() + " kg";
    }

    private static String fmt(BigDecimal v) {
        return String.format("%.2f", v);
    }

    private BigDecimal orZero(BigDecimal baseFare) {
        return baseFare != null ? baseFare : BigDecimal.valueOf(0);
    }

    private String buildSubject(BookingConfirmationEvent  confirmationEvent){

        String depDate = confirmationEvent.getDepartureDateTime() != null?
                confirmationEvent.getDepartureDateTime().format(DATE_TIME_FORMATTER) : "";
        return String.format("Booking Confirmed for | %s | %s\u2192%s | %s",
                confirmationEvent.getBookingReference(),
                confirmationEvent.getDepartureAirportCode(),
                confirmationEvent.getArrivalAirportCode(),
                depDate
        );

    }


}
