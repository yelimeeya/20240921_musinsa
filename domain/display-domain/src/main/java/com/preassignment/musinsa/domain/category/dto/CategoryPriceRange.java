package com.preassignment.musinsa.domain.category.dto;

import com.preassignment.musinsa.domain.product.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPriceRange {

  private String category;
  private List<Product> lowestPrice;
  private List<Product> highestPrice;
}
