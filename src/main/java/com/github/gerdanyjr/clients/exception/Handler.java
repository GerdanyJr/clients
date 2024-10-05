package com.github.gerdanyjr.clients.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Void> conflictExceptionHandler(ConflictException e) {
        return ResponseEntity.status(409).build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> notFoundExceptionHandler(NotFoundException e) {
        return ResponseEntity.status(404).build();
    }
}
