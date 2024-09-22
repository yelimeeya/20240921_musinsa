package com.preassignment.musinsa.display.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryPriceResponseDTO {

  private List<ProductDTO> categories;
  private String totalPrice;

  public CategoryPriceResponseDTO(List<ProductDTO> categories, String totalPrice) {
    this.categories = categories;
    this.totalPrice = totalPrice;
  }
}