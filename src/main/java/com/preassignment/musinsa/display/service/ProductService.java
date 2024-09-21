package com.preassignment.musinsa.display.service;

import com.preassignment.musinsa.display.dto.CategoryPriceRangeDTO;
import com.preassignment.musinsa.display.dto.CategoryLowestPriceDTO;
import com.preassignment.musinsa.display.entity.Category;
import com.preassignment.musinsa.display.entity.Product;
import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.repository.CategoryRepository;
import com.preassignment.musinsa.display.repository.ProductRepository;
import com.preassignment.musinsa.display.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.brandRepository = brandRepository;
    this.categoryRepository = categoryRepository;
  }

  // 카테고리별 최저가 상품 조회
  public List<CategoryLowestPriceDTO> getLowestPricesByCategory() {
    List<Product> products = productRepository.findAll();

    // 카테고리별로 최저가를 계산하여 DTO로 변환
    return products.stream()
        .collect(Collectors.groupingBy(Product::getCategory,
            Collectors.minBy((p1, p2) -> Integer.compare(p1.getPrice(), p2.getPrice()))))
        .entrySet()
        .stream()
        .map(entry -> new CategoryLowestPriceDTO(entry.getKey().getName(), entry.getValue().get().getBrand().getName(), entry.getValue().get().getPrice()))
        .collect(Collectors.toList());
  }

  // 단일 브랜드의 전체 카테고리 상품 구매 시 최저가 계산
  public int getTotalPriceByBrand(Long brandId) {
    Brand brand = brandRepository.findById(brandId)
        .orElseThrow(() -> new IllegalArgumentException("해당 브랜드를 찾을 수 없습니다."));

    return brand.getProducts().stream()
        .mapToInt(Product::getPrice)
        .sum();
  }

  // 카테고리 내 최저가 및 최고가 상품 정보를 가져오는 메서드
  public CategoryPriceRangeDTO getPriceRangeInCategory(String categoryName) {
    // 카테고리 이름으로 카테고리 객체를 찾습니다 (예: categoryRepository.findByName(categoryName))
    Category category = categoryRepository.findByName(categoryName)
        .orElseThrow(() -> new RuntimeException("Category not found"));

    // 최저가 상품 정보
    Product cheapestProduct = productRepository.findTopByCategoryOrderByPriceAsc(category);
    // 최고가 상품 정보
    Product mostExpensiveProduct = productRepository.findTopByCategoryOrderByPriceDesc(category);

    // DTO로 변환하여 반환
    return new CategoryPriceRangeDTO(
        category,
        cheapestProduct.getBrand().getName(),
        cheapestProduct.getPrice(),
        mostExpensiveProduct.getBrand().getName(),
        mostExpensiveProduct.getPrice()
    );
  }

  // 상품 추가
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  // 상품 수정
  public Product updateProduct(Long id, Product updatedProduct) {
    Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품을 찾을 수 없습니다: " + id));

    existingProduct.setCategory(updatedProduct.getCategory());
    existingProduct.setPrice(updatedProduct.getPrice());
    existingProduct.setBrand(updatedProduct.getBrand());

    return productRepository.save(existingProduct);
  }

  // 상품 삭제
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}
