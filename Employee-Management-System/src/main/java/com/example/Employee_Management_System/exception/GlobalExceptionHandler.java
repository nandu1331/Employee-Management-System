package com.example.Employee_Management_System.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage());
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", "Internal Server Error");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
