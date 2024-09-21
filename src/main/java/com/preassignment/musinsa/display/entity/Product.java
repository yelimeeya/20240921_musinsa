package com.preassignment.musinsa.display.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int price;

  @ManyToOne
  private Brand brand;

  @ManyToOne
  private Category category;

  public Product() {}

  public Product(int price, Brand brand, Category category) {
    this.price = price;
    this.brand = brand;
    this.category = category;
  }
}
