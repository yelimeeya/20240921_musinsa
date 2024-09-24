package com.preassignment.musinsa.app.service.brand;

import com.preassignment.musinsa.domain.brand.BrandCommendService;
import com.preassignment.musinsa.domain.brand.request.BrandRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {
  private final BrandCommendService brandCommendService;

  public void addBrand(BrandRequest brandRequest) {
    brandCommendService.addBrand(brandRequest);
  }

  public void updateBrand(Long id, BrandRequest brandRequest) {
    brandCommendService.updateBrand(id, brandRequest);
  }

  public void deleteBrand(Long id) {
    brandCommendService.deleteBrand(id);
  }
}
