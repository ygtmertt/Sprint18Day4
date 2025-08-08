package com.workintech.s18d1.exceptions;

public class BurgerErrorResponse {
  private String message;

  public BurgerErrorResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
