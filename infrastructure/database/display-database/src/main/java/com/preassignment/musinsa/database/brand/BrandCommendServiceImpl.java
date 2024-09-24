package com.preassignment.musinsa.database.brand;

import com.preassignment.musinsa.database.product.ProductRepository;
import com.preassignment.musinsa.domain.brand.BrandCommendService;
import com.preassignment.musinsa.domain.brand.request.BrandRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandCommendServiceImpl implements BrandCommendService {

  private final BrandRepository brandRepository;
  private final ProductRepository productRepository;

  @Override
  public void addBrand(BrandRequest brandRequest) {
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setName(brandRequest.getName());
    brandRepository.save(brandEntity);
  }

  @Override
  public void updateBrand(Long id, BrandRequest brandRequest) {
    BrandEntity brandEntity = brandRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    // 브랜드에 최소 하나의 상품이 남는지 확인
    if (productRepository.countByBrand(brandEntity) == 1) {
      throw new RuntimeException("브랜드의 카테고리에는 최소 하나의 상품이 존재해야 합니다.");
    }

    brandEntity.setName(brandRequest.getName());
    brandRepository.save(brandEntity);
  }

  @Override
  public void deleteBrand(Long id) {
    BrandEntity brandEntity = brandRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    // 브랜드에 최소 하나의 상품이 남는지 확인
    if (productRepository.countByBrand(brandEntity) == 1) {
      throw new RuntimeException("브랜드의 카테고리에는 최소 하나의 상품이 존재해야 합니다.");
    }

    brandRepository.delete(brandEntity);
  }
}
