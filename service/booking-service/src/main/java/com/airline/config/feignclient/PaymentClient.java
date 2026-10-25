package com.airline.config.feignclient;

import com.airline.playload.request.PaymentInitiatRequest;
import com.airline.playload.response.PaymentInitiatResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @PostMapping(
            value = "/api/v1/payment",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    PaymentInitiatResponse initiatePayment(
            @Valid @RequestBody PaymentInitiatRequest request
    );
}
