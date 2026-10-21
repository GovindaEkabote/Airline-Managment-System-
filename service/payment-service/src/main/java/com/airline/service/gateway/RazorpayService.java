
package com.airline.service.gateway;

import com.airline.model.Payment;
import com.airline.playload.dto.UserDto;
import com.airline.playload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class RazorpayService {

    @Value("${razorpay.api.key}")
    private String razorpayKeyId;

    @Value("${razorpay.api.secret}")
    private String razorpaySecret;

    public PaymentLinkResponse createPaymentLink(
            UserDto userDto,
            Payment payment
    ) throws RazorpayException {

        // 1. Validate payment amount
        BigDecimal amount = payment.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero"
            );
        }

        // 2. Convert INR to paise
        long amountInPaise = amount
                .setScale(2, RoundingMode.UNNECESSARY)
                .movePointRight(2)
                .longValueExact();

        // 3. Initialize Razorpay client
        RazorpayClient razorpayClient =
                new RazorpayClient(razorpayKeyId, razorpaySecret);

        // 4. Build payment-link request
        JSONObject request = new JSONObject();
        request.put("amount", amountInPaise);
        request.put("currency", "INR");
        request.put(
                "description",
                "Airline booking payment: " + payment.getTransactionId()
        );

        // 5. Customer details
        JSONObject customer = new JSONObject();
        customer.put("name", userDto.getFullName());
        customer.put("email", userDto.getEmail());

        if (userDto.getPhone() != null
                && !userDto.getPhone().isBlank()) {
            customer.put("contact", userDto.getPhone());
        }

        request.put("customer", customer);

        // 6. Notification settings
        JSONObject notify = new JSONObject();
        notify.put("email", true);
        notify.put(
                "sms",
                userDto.getPhone() != null
                        && !userDto.getPhone().isBlank()
        );

        request.put("notify", notify);
        request.put("reminder_enable", true);

        JSONObject notes= new JSONObject();
        notes.put("user_id", userDto.getId());
        notes.put("payment_id", payment.getId());
        notes.put("booking_id", payment.getBookingId());

        request.put("notes", notes);

        // 7. Create payment link
        PaymentLink paymentLink =
                razorpayClient.paymentLink.create(request);

        String paymentLinkId = paymentLink.get("id");
        String paymentUrl = paymentLink.get("short_url");

        // 8. Return payment-link details
        return PaymentLinkResponse.builder()
                .paymentLinkId(paymentLinkId)
                .paymentUrl(paymentUrl)
                .build();
    }


    public JSONObject fetchPaymentDetails(String  paymentId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpaySecret);
        com.razorpay.Payment payment
                = razorpayClient.payments.fetch(paymentId);

        return payment.toJson();
    }
}
