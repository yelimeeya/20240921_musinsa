package com.preassignment.musinsa.display.service;

import com.preassignment.musinsa.display.dto.BrandRequestDTO;
import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.repository.BrandRepository;
import com.preassignment.musinsa.display.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

  private final ProductRepository productRepository;
  private final BrandRepository brandRepository;

  @Autowired
  public BrandService(ProductRepository productRepository, BrandRepository brandRepository) {
    this.productRepository = productRepository;
    this.brandRepository = brandRepository;
  }

  // 브랜드 추가
  public void addBrand(BrandRequestDTO brandRequest) {
    Brand brand = new Brand(brandRequest.getName());
    brandRepository.save(brand);
  }

  // 브랜드 수정
  public void updateBrand(Long id, BrandRequestDTO brandRequest) {
    Brand brand = brandRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    // 브랜드의 카테고리에 최소 하나의 상품이 남는지 확인
    if (productRepository.countByBrand(brand) == 1) {
      throw new RuntimeException("브랜드의 카테고리에는 최소 하나의 상품이 존재해야 합니다.");
    }

    brand.setName(brandRequest.getName());
    brandRepository.save(brand);
  }

  // 브랜드 삭제
  public void deleteBrand(Long id) {
    Brand brand = brandRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    // 브랜드의 카테고리에 최소 하나의 상품이 남는지 확인
    if (productRepository.countByBrand(brand) == 1) {
      throw new RuntimeException("브랜드의 카테고리에는 최소 하나의 상품이 존재해야 합니다.");
    }

    brandRepository.delete(brand);
  }
}