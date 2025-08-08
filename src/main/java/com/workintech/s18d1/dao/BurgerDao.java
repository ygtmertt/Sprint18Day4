package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.Burger;

import java.util.List;

public interface BurgerDao {
  Burger save(Burger burger);
  List<Burger> findAll();
  Burger findById(long id);
  Burger update(Burger burger);
  Burger remove(long id);
  List<Burger> findByPrice(double price);
  List<Burger> findByBreadType(com.workintech.s18d1.entity.BreadType breadType);
  List<Burger> findByContent(String content);
}
