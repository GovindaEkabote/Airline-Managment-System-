package com.airline.service;

import com.airline.playload.dto.PaymentDTO;
import com.airline.playload.request.PaymentInitiatRequest;
import com.airline.playload.request.PaymentVerifyRequest;
import com.airline.playload.response.PaymentInitiatResponse;
import com.razorpay.RazorpayException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    PaymentInitiatResponse initiatePayment(PaymentInitiatRequest request) throws RazorpayException;
    PaymentDTO verifyPayment(PaymentVerifyRequest request) throws RazorpayException;

    Page<PaymentDTO> getAllPayments(Pageable pageable);

    Map<Long, PaymentDTO> getPaymentsByBookingIds(List<Long> bookingIds);
}
