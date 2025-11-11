package com.devWebII.entrega_1.controller;

import com.devWebII.entrega_1.exception.BusinessException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.devWebII.entrega_1.exception.RecursoNaoEncontradoException;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handleBusiness(BusinessException ex) {
    Map<String,Object> body = new HashMap<>();
    body.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  // Violação de integridade (FK/único etc.)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrity(DataIntegrityViolationException ex) {
    Map<String,Object> body = new HashMap<>();
    body.put("error", "Registro em uso. Exclua os vínculos antes de remover.");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(body); // 409
  }

  // Erros de formatação do JSON (body inválido)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleNotReadable(HttpMessageNotReadableException ex) {
    Map<String,Object> body = new HashMap<>();
    body.put("error", "Corpo da requisição inválido.");
    return ResponseEntity.badRequest().body(body);
  }

  // Bean Validation fora de @RequestBody (ex.: @RequestParam, @PathVariable)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String,Object> body = new HashMap<>();
    body.put("error", "Dados inválidos.");
    body.put("details", ex.getConstraintViolations()
                          .stream().map(v -> v.getPropertyPath()+": "+v.getMessage())
                          .toList());
    return ResponseEntity.badRequest().body(body);
  }


  // Bean Validation em @RequestBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    Map<String,Object> body = new HashMap<>();
    body.put("error", "Dados inválidos.");
    body.put("details", ex.getBindingResult().getAllErrors()
                          .stream().map(e -> ((FieldError)e).getField()+": "+e.getDefaultMessage())
                          .toList());
    return ResponseEntity.badRequest().body(body);
  }

  // Recurso não encontrado
  @ExceptionHandler(RecursoNaoEncontradoException.class)
  public ResponseEntity<?> handleNotFound(RecursoNaoEncontradoException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }
}
