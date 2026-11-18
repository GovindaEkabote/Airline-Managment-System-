package com.airline.config.feignclient;

import com.airline.enums.CabinClassType;
import com.airline.playload.response.CabinClassResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SEAT-SERVICE")
public interface SeatClient {

    @GetMapping("/api/v1/cabins/aircraft/{id}/name/{cabinClassType}")
    CabinClassResponse getCabinClassByAircraftIdAndName(
            @PathVariable("cabinClassType") CabinClassType cabinClassType,
            @PathVariable Long id
    );
}
