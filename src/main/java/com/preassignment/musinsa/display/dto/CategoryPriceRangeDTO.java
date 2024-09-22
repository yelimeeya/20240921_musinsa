package com.preassignment.musinsa.display.dto;

import com.preassignment.musinsa.display.entity.Category;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryPriceRangeDTO {

  private String category;
  private List<ProductDTO> lowestPrice;
  private List<ProductDTO> highestPrice;

  public CategoryPriceRangeDTO(String category, List<ProductDTO> lowestPrice,
      List<ProductDTO> highestPrice) {
    this.category = category;
    this.lowestPrice = lowestPrice;
    this.highestPrice = highestPrice;
  }
}
