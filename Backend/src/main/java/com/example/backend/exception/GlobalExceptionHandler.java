package com.example.backend.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(
            BadRequestException ex) {

        return buildResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> notFound(
            ResourceNotFoundException ex) {

        return buildResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        return ResponseEntity.badRequest().body(errors);
    }

    private ResponseEntity<?> buildResponse(
            String message,
            HttpStatus status) {

        Map<String, Object> body =
                new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}