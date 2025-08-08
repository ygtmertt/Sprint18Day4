package com.workintech.s18d1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "burger", schema = "fsweb")
public class Burger {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Double price;

  @Column(name = "is_vegan")
  private Boolean isVegan;

  @Enumerated(EnumType.STRING)
  @Column(name = "bread_type")
  private BreadType breadType;

  private String contents;
}