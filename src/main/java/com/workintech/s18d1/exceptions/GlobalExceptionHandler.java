package com.workintech.s18d1.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler
  public ResponseEntity<BurgerErrorResponse> handleException(BurgerException ex) {
    logger.error("BurgerException occurred: {}", ex.getMessage());
    return new ResponseEntity<>(new BurgerErrorResponse(ex.getMessage()), ex.getHttpStatus());
  }

  @ExceptionHandler
  public ResponseEntity<BurgerErrorResponse> handleException(Exception ex) {
    logger.error("Unhandled exception: {}", ex.getMessage());
    return new ResponseEntity<>(new BurgerErrorResponse("Internal Server Error"),
        org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
