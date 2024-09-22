package com.preassignment.musinsa.display.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrandPriceResponseDTO {
  private String brand;
  private List<ProductDTO> categories;
  private String totalPrice;

  public BrandPriceResponseDTO(String brand, List<ProductDTO> categories, String totalPrice) {
    this.brand = brand;
    this.categories = categories;
    this.totalPrice = totalPrice;
  }
}