package com.preassignment.musinsa.display.dto;

import com.preassignment.musinsa.display.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryPriceRangeDTO {

  private String category;
  private String cheapestBrand;
  private int cheapestPrice;
  private String mostExpensiveBrand;
  private int mostExpensivePrice;

  // 수정된 생성자: Category 객체에서 이름을 추출
  public CategoryPriceRangeDTO(Category category, String cheapestBrand, int cheapestPrice,
      String mostExpensiveBrand, int mostExpensivePrice) {
    // Category 객체에서 이름을 추출하여 저장
    this.category = category.getName();
    this.cheapestBrand = cheapestBrand;
    this.cheapestPrice = cheapestPrice;
    this.mostExpensiveBrand = mostExpensiveBrand;
    this.mostExpensivePrice = mostExpensivePrice;
  }

  // Getters and Setters

}
