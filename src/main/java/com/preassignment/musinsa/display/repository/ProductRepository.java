package com.preassignment.musinsa.display.repository;

import com.preassignment.musinsa.display.entity.Category;
import com.preassignment.musinsa.display.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  // 카테고리에서 최저가 상품 조회
  Product findTopByCategoryOrderByPriceAsc(Category category);

  // 카테고리에서 최고가 상품 조회
  Product findTopByCategoryOrderByPriceDesc(Category category);
}
