package com.preassignment.musinsa.database.product;

import com.preassignment.musinsa.database.brand.BrandEntity;
import com.preassignment.musinsa.database.category.CategoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  List<ProductEntity> findByCategoryName(String categoryName);

  // 카테고리와 브랜드로 상품 수 조회
  long countByCategoryAndBrand(CategoryEntity category, BrandEntity brand);

  // 브랜드로 상품 수 조회
  long countByBrand(BrandEntity brand);
}

