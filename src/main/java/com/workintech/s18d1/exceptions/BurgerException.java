package com.workintech.s18d1.exceptions;

import org.springframework.http.HttpStatus;

public class BurgerException extends RuntimeException {

  private HttpStatus httpStatus;

  public BurgerException(String message, HttpStatus status) {
    super(message);
    this.httpStatus = status;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus status) {
    this.httpStatus = status;
  }
}
