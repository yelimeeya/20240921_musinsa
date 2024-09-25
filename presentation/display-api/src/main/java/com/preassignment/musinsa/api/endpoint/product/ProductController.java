package com.preassignment.musinsa.api.endpoint.product;

import com.preassignment.musinsa.app.service.product.ProductService;
import com.preassignment.musinsa.domain.brand.respose.BrandPriceResponse;
import com.preassignment.musinsa.domain.category.dto.CategoryPriceRange;
import com.preassignment.musinsa.domain.category.response.CategoryPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  // 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
  @GetMapping("/category/lowest-prices")
  public ResponseEntity<CategoryPriceResponse> getLowestPricesByCategory() {
    CategoryPriceResponse response = productService.getLowestPricesByCategory();
    return ResponseEntity.ok(response);
  }

  // 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
  @GetMapping("/brand/lowest-prices")
  public ResponseEntity<BrandPriceResponse> getLowestPriceByBrand() {
    BrandPriceResponse response = productService.getLowestPricesByBrand();
    return ResponseEntity.ok(response);
  }

  // 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
  @GetMapping("/category/price-range")
  public ResponseEntity<CategoryPriceRange> getPriceRangeByCategory(
      @RequestParam String categoryName) {
    CategoryPriceRange response = productService.getPriceRangeByCategory(categoryName);
    return ResponseEntity.ok(response);
  }
}
