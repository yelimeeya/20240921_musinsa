package com.preassignment.musinsa.display.service;

import com.preassignment.musinsa.display.dto.BrandRequestDTO;
import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

  private final BrandRepository brandRepository;

  @Autowired
  public BrandService(BrandRepository brandRepository) {
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
    brand.setName(brandRequest.getName());
    brandRepository.save(brand);
  }

  // 브랜드 삭제
  public void deleteBrand(Long id) {
    Brand brand = brandRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));
    brandRepository.delete(brand);
  }
}