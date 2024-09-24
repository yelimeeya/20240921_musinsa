package com.preassignment.musinsa.app.service.product;

import com.preassignment.musinsa.domain.brand.respose.BrandPriceResponse;
import com.preassignment.musinsa.domain.category.dto.CategoryPriceRange;
import com.preassignment.musinsa.domain.category.response.CategoryPriceResponse;
import com.preassignment.musinsa.domain.product.ProductCommendService;
import com.preassignment.musinsa.domain.product.ProductQueryService;
import com.preassignment.musinsa.domain.product.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
private final ProductCommendService productCommendService;
private final ProductQueryService productQueryService;

  public CategoryPriceResponse getLowestPricesByCategory() {
    return productQueryService.getLowestPricesByCategory();
  }

  public BrandPriceResponse getLowestPricesByBrand() {
    return productQueryService.getLowestPricesByBrand();
  }

  public CategoryPriceRange getPriceRangeByCategory(String categoryName) {
    return productQueryService.getPriceRangeByCategory(categoryName);
  }

  public void addProduct(ProductRequest productRequest) {
    productCommendService.addProduct(productRequest);
  }

  public void updateProduct(Long id, ProductRequest productRequest) {
    productCommendService.updateProduct(id, productRequest);
  }

  public void deleteProduct(Long id) {
    productCommendService.deleteProduct(id);

  }
}
