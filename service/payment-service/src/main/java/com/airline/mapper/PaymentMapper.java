package com.airline.mapper;

import com.airline.enums.PaymentGateway;
import com.airline.model.Payment;
import com.airline.playload.dto.PaymentDTO;

import java.time.ZoneId;

public class PaymentMapper {

    public static PaymentDTO toDTO(Payment payment){

        if (payment==null){
            return null;
        }

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setPaymentGateway(payment.getPaymentGateway());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setTransactionId(payment.getTransactionId());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus());
        paymentDTO.setUserId(payment.getUserId());
        paymentDTO.setBookingId(payment.getBookingId());

        if (payment.getPaidAt() != null){
            paymentDTO.setCompletedAt(payment.getPaidAt()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
        }

        if (payment.getCreatedAt() != null){
            paymentDTO.setCreatedAt(payment.getCreatedAt()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
            paymentDTO.setInitiatedAt(payment.getCreatedAt()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
        }

        if (payment.getUpdatedAt() != null){
            paymentDTO.setUpdatedAt(payment.getUpdatedAt()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
        }

        return paymentDTO;
    }
}
