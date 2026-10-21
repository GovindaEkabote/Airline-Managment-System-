package com.airline.playload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkResponse {

    private Long id;
    private String paymentLinkId;
    private String paymentUrl;

}
