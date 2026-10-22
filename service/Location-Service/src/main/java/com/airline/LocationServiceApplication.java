package com.airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LocationServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(LocationServiceApplication.class, args);

        System.out.println("Location Service Started");
	}

}
