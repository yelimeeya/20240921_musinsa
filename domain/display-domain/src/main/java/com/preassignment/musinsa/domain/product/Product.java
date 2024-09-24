package com.preassignment.musinsa.domain.product;

import com.preassignment.musinsa.domain.RootDomain;
import com.preassignment.musinsa.domain.brand.Brand;
import com.preassignment.musinsa.domain.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RootDomain
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {
  private int price;
  private Brand brand;
  private Category category;
}
