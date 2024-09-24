package com.preassignment.musinsa.domain.product;

import com.preassignment.musinsa.domain.brand.respose.BrandPriceResponse;
import com.preassignment.musinsa.domain.category.dto.CategoryPriceRange;
import com.preassignment.musinsa.domain.category.response.CategoryPriceResponse;

public interface ProductQueryService {

  CategoryPriceResponse getLowestPricesByCategory();

  BrandPriceResponse getLowestPricesByBrand();

  CategoryPriceRange getPriceRangeByCategory(String categoryName);
}
