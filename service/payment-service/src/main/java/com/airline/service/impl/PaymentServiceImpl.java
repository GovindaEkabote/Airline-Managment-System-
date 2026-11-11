package com.airline.service.impl;

import com.airline.config.feignClient.UserClient;
import com.airline.enums.PaymentGateway;
import com.airline.enums.PaymentStatus;
import com.airline.event.PaymentEventProducer;
import com.airline.exception.ResourceNotFoundException;
import com.airline.mapper.PaymentMapper;
import com.airline.model.Payment;
import com.airline.playload.dto.PaymentDTO;
import com.airline.playload.dto.UserDto;
import com.airline.playload.request.PaymentInitiatRequest;
import com.airline.playload.request.PaymentVerifyRequest;
import com.airline.playload.response.PaymentInitiatResponse;
import com.airline.playload.response.PaymentLinkResponse;
import com.airline.repository.PaymentRepository;
import com.airline.service.PaymentService;
import com.airline.service.gateway.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final PaymentEventProducer paymentEventProducer;
    private final UserClient client;

    @Override
    public PaymentInitiatResponse initiatePayment(PaymentInitiatRequest request) throws RazorpayException{
        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(payment -> {
                    if (payment.getPaymentStatus() == PaymentStatus.SUCCESS){
                        throw  new RuntimeException("Payment already completed for this booking id");
                    }
                });
        Payment payment
                 = Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .providerPaymentId(request.getProviderPaymentId())
                .paymentGateway(request.getPaymentGateway())
                .paymentStatus(PaymentStatus.PENDING)
                .transactionId(generateTransactionId())
                .build();

        payment  = paymentRepository.save(payment);

        PaymentInitiatResponse paymentInitiatResponse =
                PaymentInitiatResponse.builder()
                        .id(payment.getId())
                        .paymentGateway(request.getPaymentGateway())
                        .transactionId(payment.getTransactionId())
                        .amount(request.getAmount())
                        .descriptiom(request.getDescription())
                        .success(true)
                        .message("Payment initiates successfully")
                        .build();

        if (request.getPaymentGateway() == PaymentGateway.RAZORPAY){
            // fetch user details using feign client
            UserDto user = client.getUserByIds(request.getUserId());

            // create razopay payment link using razopay service
            PaymentLinkResponse response = razorpayService.createPaymentLink(user, payment);

            // set payment link to payment initiat response
            paymentInitiatResponse.setRazorpayOrderId(response.getPaymentLinkId());
            paymentInitiatResponse.setCheckoutUrl(response.getPaymentUrl());

        }
        return paymentInitiatResponse;
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest request) throws RazorpayException {
        JSONObject  paymentDetails= razorpayService.fetchPaymentDetails(
                request.getRazorpayPaymentId()
        );
        String status= paymentDetails.getString("status");
        JSONObject notes= paymentDetails.getJSONObject("notes");
        Long paymentId=Long.parseLong(notes.optString("payment_id"));

        Payment payment=paymentRepository.findById(paymentId).orElseThrow(
                () -> new ResourceNotFoundException("Not Found")
        );
        boolean isValid="captured".equalsIgnoreCase(status);

        if (isValid){
            if (payment.getProviderPaymentId().equals(PaymentGateway.RAZORPAY)){
                payment.setProviderPaymentId(request.getRazorpayPaymentId());
            }
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            // publish kafka event payment completed
            paymentEventProducer.sendPaymentCompletedEvent(payment);

        }else{
            payment.setPaymentStatus(PaymentStatus.FAILED);
            payment.setPaymentFailureReason("Payment Verification Failed");
            paymentRepository.save(payment);

            // publish kafka event payment failed
            paymentEventProducer.sendPaymentFailed(payment);
        }
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(
                        PaymentMapper::toDTO
                );
    }

    @Override
    public Map<Long, PaymentDTO> getPaymentsByBookingIds(
            List<Long> bookingIds) {
        return paymentRepository.findByBookingIdIn(bookingIds)
                .stream()
                .collect(Collectors.toMap(Payment::getId, PaymentMapper::toDTO));
    }


    private String generateTransactionId(){
        return "TXN_" +
                System.currentTimeMillis() +
                "_"+
                UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase();
    }
}
