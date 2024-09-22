package com.preassignment.musinsa.display.repository;

import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.entity.Category;
import com.preassignment.musinsa.display.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  // 카테고리 이름으로 상품 조회
  List<Product> findByCategoryName(String categoryName);

  // 카테고리와 브랜드로 상품 수 조회
  long countByCategoryAndBrand(Category category, Brand brand);

  // 브랜드로 상품 수 조회
  long countByBrand(Brand brand);
}
