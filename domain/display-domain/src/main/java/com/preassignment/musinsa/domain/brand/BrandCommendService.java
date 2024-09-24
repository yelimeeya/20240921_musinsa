package com.preassignment.musinsa.domain.brand;

import com.preassignment.musinsa.domain.brand.request.BrandRequest;

public interface BrandCommendService {

  void addBrand(BrandRequest brandRequest);

  void updateBrand(Long id, BrandRequest brandRequest);

  void deleteBrand(Long id);
}
