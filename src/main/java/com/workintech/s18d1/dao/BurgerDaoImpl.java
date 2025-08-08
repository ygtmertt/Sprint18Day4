package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class BurgerDaoImpl implements BurgerDao {

  private final EntityManager entityManager;

  @Autowired
  public BurgerDaoImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @Override
  @Transactional
  public Burger save(Burger burger) {
    entityManager.persist(burger);
    return burger;
  }

  @Override
  public Burger findById(long id) {
    return entityManager.find(Burger.class, id);
  }

  @Override
  public List<Burger> findAll() {
    String sqlStatement = "SELECT b FROM Burger b";
    TypedQuery<Burger> query = entityManager.createQuery(sqlStatement, Burger.class);
    return query.getResultList();
  }

  @Override
  public List<Burger> findByPrice(double price) {
    String sqlStatement = "SELECT b FROM Burger b WHERE b.price>=:price ORDER BY b.price DESC";
    TypedQuery<Burger> query = entityManager.createQuery(sqlStatement, Burger.class);
    query.setParameter("price", price);
    return query.getResultList();
  }

  @Override
  public List<Burger> findByBreadType(BreadType breadType) {
    String sqlStatement = "SELECT b FROM Burger b WHERE b.breadType=:breadType ORDER BY b.name ASC";
    TypedQuery<Burger> query = entityManager.createQuery(sqlStatement, Burger.class);
    query.setParameter("breadType", breadType);
    return query.getResultList();
  }


  @Override
  public List<Burger> findByContent(String content) {
    String sqlStatement = "SELECT b FROM Burger b WHERE b.contents LIKE CONCAT('%', :content, '%')";
    TypedQuery<Burger> query = entityManager.createQuery(sqlStatement, Burger.class);
    query.setParameter("content", content);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Burger update(Burger burger) {
    return entityManager.merge(burger);
  }

  @Override
  @Transactional
  public Burger remove(long id) {
    Burger deletedBurger = entityManager.find(Burger.class, id);
    if(deletedBurger != null) {
      entityManager.remove(deletedBurger);
    }
    return deletedBurger;
  }
}
