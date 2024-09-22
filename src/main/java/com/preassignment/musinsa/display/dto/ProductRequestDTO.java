package com.preassignment.musinsa.display.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDTO {

  private Long category;
  private Long brand;
  private int price;

  public ProductRequestDTO(Long category, Long brand, int price) {
    this.category = category;
    this.brand = brand;
    this.price = price;
  }
}