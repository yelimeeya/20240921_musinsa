package com.preassignment.musinsa.app.service.brand;

import com.preassignment.musinsa.cache.service.RedisService;
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
  private final RedisService redisService;

  public void addBrand(BrandRequest brandRequest) {
    brandCommendService.addBrand(brandRequest);

    redisService.delValue("lowestPricesByCategory");
    redisService.delValue("lowestPricesByBrand");
    redisService.delValuesByPattern("priceRangeByCategory:*");
  }

  public void updateBrand(Long id, BrandRequest brandRequest) {
    brandCommendService.updateBrand(id, brandRequest);

    redisService.delValue("lowestPricesByCategory");
    redisService.delValue("lowestPricesByBrand");
    redisService.delValuesByPattern("priceRangeByCategory:*");
  }

  public void deleteBrand(Long id) {
    brandCommendService.deleteBrand(id);

    redisService.delValue("lowestPricesByCategory");
    redisService.delValue("lowestPricesByBrand");
    redisService.delValuesByPattern("priceRangeByCategory:*");
  }
}
