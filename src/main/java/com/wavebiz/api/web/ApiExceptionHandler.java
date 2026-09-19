package com.wavebiz.api.web;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(NoSuchElementException.class)
  ResponseEntity<ProblemDetail> notFound(NoSuchElementException ex) { return problem(HttpStatus.NOT_FOUND, ex.getMessage()); }
  @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
  ResponseEntity<ProblemDetail> badRequest(RuntimeException ex) { return problem(HttpStatus.BAD_REQUEST, ex.getMessage()); }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException ex) { return problem(HttpStatus.BAD_REQUEST, "Request validation failed"); }
  private ResponseEntity<ProblemDetail> problem(HttpStatus status, String detail) {
    ProblemDetail body = ProblemDetail.forStatusAndDetail(status, detail); body.setType(URI.create("https://wavebiz.app/problems/" + status.value()));
    return ResponseEntity.status(status).body(body);
  }
}

