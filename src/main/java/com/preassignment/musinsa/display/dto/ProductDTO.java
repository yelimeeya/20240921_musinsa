package com.preassignment.musinsa.display.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {

  private String category;
  private String brand;
  private String price;

  public ProductDTO(String category, String brand, String price) {
    this.category = category;
    this.brand = brand;
    this.price = price;
  }

}


