package com.preassignment.musinsa.display.controller;

import com.preassignment.musinsa.display.dto.CategoryLowestPriceDTO;
import com.preassignment.musinsa.display.dto.CategoryPriceRangeDTO;
import com.preassignment.musinsa.display.entity.Product;
import com.preassignment.musinsa.display.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public List<CategoryLowestPriceDTO> getLowestPricesByCategory() {
    return productService.getLowestPricesByCategory();
  }

  // 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
  @GetMapping("/brand/{brandId}/lowest-total-price")
  public int getLowestTotalPriceByBrand(@PathVariable Long brandId) {
    return productService.getTotalPriceByBrand(brandId);
  }

  // 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
  @GetMapping("/category/{category}/price-range")
  public CategoryPriceRangeDTO getPriceRangeInCategory(@PathVariable String category) {
    return productService.getPriceRangeInCategory(category);
  }


  // 상품 추가
  @PostMapping
  public Product addProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }

  // 상품 수정
  @PutMapping("/{id}")
  public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
    return productService.updateProduct(id, updatedProduct);
  }

  // 상품 삭제
  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
  }
}
