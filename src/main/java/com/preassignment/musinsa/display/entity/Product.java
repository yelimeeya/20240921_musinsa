package com.preassignment.musinsa.display.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
  @Column
  private int price;
  @JoinColumn
  @ManyToOne
  private Brand brand;
  @JoinColumn
  @ManyToOne
  private Category category;

  public Product() {}

  public Product(int price, Brand brand, Category category) {
    this.price = price;
    this.brand = brand;
    this.category = category;
  }
}
