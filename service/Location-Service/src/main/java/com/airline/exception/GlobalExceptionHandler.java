package com.airline.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String TRACE_ID = "traceId";

    // ============================================================
    // Resource Not Found
    // ============================================================

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
            ) {
        log.warn(
                "Resource not found: {}",
                ex.getMessage()
        );
        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request,
                null
        );
    }

    // ============================================================
    // Duplicate Resource
    // ============================================================
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException ex,
            HttpServletRequest request
    ){
        log.warn(
                "Duplicate resource: {}",
                ex.getMessage()
        );
        return buildResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request,
                null
        );
    }

    // ============================================================
    // Validation Errors
    // ============================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldError> fieldErrors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(this::toFieldError)
                        .toList();

        log.warn(
                "Validation failed: {}",
                fieldErrors
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request,
                fieldErrors
        );
    }

    // ============================================================
    // Invalid Parameter Type
    // ============================================================
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String message = String.format(
                "Parameter '%s' has invalid value '%s'",
                ex.getName(),
                ex.getValue()
        );

        log.warn(message);

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request,
                null
        );
    }

    // ============================================================
    // Invalid JSON Request
    // ============================================================

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.warn(
                "Invalid request body: {}",
                ex.getMessage()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid request body",
                request,
                null
        );
    }

    // ============================================================
    // Database Constraint / Integrity Error
    // ============================================================

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.warn(
                "Data integrity violation",
                ex
        );

        return buildResponse(
                HttpStatus.CONFLICT,
                "Request conflicts with existing data",
                request,
                null
        );
    }

    // ============================================================
    // Endpoint Not Found
    // ============================================================

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        log.warn(
                "Endpoint not found: {}",
                request.getRequestURI()
        );

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                request,
                null
        );
    }

    // ============================================================
    // Generic Exception
    // ============================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error(
                "Unhandled exception",
                ex
        );

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                request,
                null
        );
    }

    // Convert Spring FieldError -> Common FieldError
    private ErrorResponse.FieldError toFieldError(
            FieldError fieldError) {

        return ErrorResponse.FieldError.builder()
                .field(fieldError.getField())
                .rejectedValue(
                        fieldError.getRejectedValue() == null
                                ? null
                                : fieldError.getRejectedValue().toString()
                )
                .message(fieldError.getDefaultMessage())
                .build();
    }


    // Build Error Response
    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            List<ErrorResponse.FieldError> fieldErrors) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(java.time.Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .traceId(MDC.get(TRACE_ID))
                .fieldErrors(fieldErrors)
                .build();

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
