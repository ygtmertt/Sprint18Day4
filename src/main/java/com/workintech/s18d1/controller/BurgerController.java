package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.util.BurgerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/workintech/burgers")
public class BurgerController {

  private final BurgerDao burgerDao;

  @Autowired
  public BurgerController(BurgerDao burgerDao) {
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

  // PUT -> sadece boş bırakıyoruz, çünkü sınıf seviyesinde /workintech/burgers var
  @PutMapping
  public Burger updateBurger(@RequestBody Burger burger) {
    return burgerDao.update(burger);
  }

  @DeleteMapping("/{id}")
  public Burger deleteBurger(@PathVariable long id) {
    Burger deletedBurger = burgerDao.findById(id);
    if (deletedBurger != null) {
      burgerDao.remove(id);
      return deletedBurger;
    }
    return null;
  }

  @GetMapping("/price/{price}")
  public List<Burger> findBurgerByPrice(@PathVariable double price) {
    return burgerDao.findByPrice(price);
  }

  @GetMapping("/breadType/{breadType}")
  public List<Burger> findBurgerByBreadType(@PathVariable BreadType breadType) {
    return burgerDao.findByBreadType(breadType);
  }

  @GetMapping("/content/{content}")
  public List<Burger> findBurgerByContent(@PathVariable String content) {
    return burgerDao.findByContent(content);
  }
}
