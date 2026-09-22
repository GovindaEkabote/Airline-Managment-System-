package com.airline.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    Instant timestamp;
    int status;
    String error;
    String message;
    String path;
    String  traceId;
    List<FieldError> fieldErrors;


    @Value
    @Builder
    public static class FieldError {
        String field;
        String rejectedValue;
        String message;
    }

    public static ErrorResponse of(
            int status,
            String error,
            String message,
            String path,
            String traceId) {

        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .traceId(traceId)
                .build();
    }
}
