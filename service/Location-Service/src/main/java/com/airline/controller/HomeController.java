package com.airline.controller;

import com.airline.playload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping
    public ApiResponse HomeController(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(200);
        apiResponse.setMessage("Hello World");
        apiResponse.setData(LocalDateTime.now());
        return apiResponse;
    }
}
