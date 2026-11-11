package com.airline.config.feignClient;

import com.airline.playload.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/v1/users/{userId}")
    public UserDto getUserByIds(@PathVariable Long userId);


}
