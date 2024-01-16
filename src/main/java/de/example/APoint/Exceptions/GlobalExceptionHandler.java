package de.example.APoint.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DeadlineExceededException.class)
    public ResponseEntity<String> handleDeadlineExceeded(DeadlineExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN) // 403 Forbidden status
                .body(ex.getMessage());
    }
}
