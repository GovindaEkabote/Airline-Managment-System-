package com.airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class PaymentServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(PaymentServiceApplication.class, args);
        System.out.println("Payment Service pplication Started..");
	}

}
