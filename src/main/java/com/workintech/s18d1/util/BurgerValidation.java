package com.workintech.s18d1.util;

import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import org.springframework.http.HttpStatus;

public class BurgerValidation {

  public static void validateBurger(Burger burger) {
    if (burger == null) {
      throw new BurgerException("Burger object cannot be null", HttpStatus.BAD_REQUEST);
    }
    if (burger.getName() == null || burger.getName().isBlank()) {
      throw new BurgerException("Burger name cannot be empty", HttpStatus.BAD_REQUEST);
    }
    if (burger.getPrice() <= 0) {
      throw new BurgerException("Burger price must be greater than 0", HttpStatus.BAD_REQUEST);
    }
  }
}
