package com.airline.playload.response;

import lombok.Data;

@Data
public class ApiResponse {

    private int statusCode;
    private String message;
    private Object data;
}
