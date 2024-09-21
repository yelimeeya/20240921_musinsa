package com.preassignment.musinsa.display.service;

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

  // 브랜드 저장 (추가)
  public Brand saveBrand(Brand brand) {
    return brandRepository.save(brand);
  }

  // 브랜드 수정
  public Brand updateBrand(Long id, Brand updatedBrand) {
    Brand existingBrand = brandRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 ID의 브랜드를 찾을 수 없습니다: " + id));

    existingBrand.setName(updatedBrand.getName());
    // 필요하다면 다른 필드들도 업데이트 가능

    return brandRepository.save(existingBrand);
  }

  // 브랜드 삭제
  public void deleteBrand(Long id) {
    if (!brandRepository.existsById(id)) {
      throw new IllegalArgumentException("해당 ID의 브랜드를 찾을 수 없습니다: " + id);
    }
    brandRepository.deleteById(id);
  }
}
