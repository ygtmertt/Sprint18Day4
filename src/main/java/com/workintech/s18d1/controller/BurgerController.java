package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDaoImpl;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.util.BurgerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/workintech/burgers")

public class BurgerController {

  private final BurgerDaoImpl burgerDao;

  @Autowired
  public BurgerController(BurgerDaoImpl burgerDao) {
    this.burgerDao = burgerDao;
  }

  @GetMapping
  public List<Burger> getAllBurgers() {
    return burgerDao.findAll();
  }

  @GetMapping("/{id}")
  public Burger getBurgerById(@PathVariable long id) {
    return burgerDao.findById(id);
  }

  @PostMapping
  public Burger createBurger(@RequestBody Burger burger) {
    BurgerValidation.validateBurger(burger);
    return burgerDao.save(burger);
  }

  @PutMapping("/{id}")
  public Burger updateBurger(@PathVariable int id, @RequestBody Burger burger) {
    BurgerValidation.validateBurger(burger);
    Burger existingBurger = burgerDao.findById(id);
    burger.setId(existingBurger.getId()); // update aynı id'de yapılmalı
    return burgerDao.save(burger);
  }

  @DeleteMapping("/{id}")
  public Burger deleteBurger(@PathVariable long id) {
    Burger deletedBurger = burgerDao.findById(id);
    if(deletedBurger != null) {
      burgerDao.remove(id);
      return deletedBurger;
    }
    else {
      return null;
    }
  }

  @GetMapping("/{findByPrice}")
  public List<Burger> findBurgerByPrice(@RequestBody double price) {
    return burgerDao.findByPrice(price);
  }

  @GetMapping("/{findByBreadType}")
  public List<Burger> findBurgerByBreadType(@RequestBody BreadType breadType) {
    return burgerDao.findByBreadType(breadType);
  }

  @GetMapping("/{findByContent}")
  public List<Burger> findBurgerByContent(@RequestBody String content) {
    return burgerDao.findByContent(content);
  }
}
