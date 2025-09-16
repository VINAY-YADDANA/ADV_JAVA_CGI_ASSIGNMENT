package com.example.inventory.config;

import com.example.inventory.dto.ApiError;
import com.example.inventory.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiError handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
    return new ApiError(404, "Not Found", ex.getMessage(), req.getRequestURI(), null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    List<String> errors = ex.getBindingResult().getFieldErrors()
      .stream().map(fe -> fe.getField() + ": " + fe.getDefaultMessage()).toList();
    return new ApiError(400, "Bad Request", "Validation failed", req.getRequestURI(), errors);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
    List<String> errors = ex.getConstraintViolations()
      .stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList();
    return new ApiError(400, "Bad Request", "Validation failed", req.getRequestURI(), errors);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiError handleOther(Exception ex, HttpServletRequest req) {
    return new ApiError(500, "Internal Server Error", ex.getMessage(), req.getRequestURI(), null);
  }
}
