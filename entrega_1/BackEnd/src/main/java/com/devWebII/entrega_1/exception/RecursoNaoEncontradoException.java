package com.devWebII.entrega_1.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String message) { super(message); }
    @ExceptionHandler(RecursoNaoEncontradoException.class)
public ResponseEntity<?> handleNotFound(RecursoNaoEncontradoException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
}
}
