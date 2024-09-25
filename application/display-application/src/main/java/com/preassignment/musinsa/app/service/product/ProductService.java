package com.preassignment.musinsa.app.service.product;

import com.preassignment.musinsa.cache.service.RedisService;
import com.preassignment.musinsa.domain.brand.respose.BrandPriceResponse;
import com.preassignment.musinsa.domain.category.dto.CategoryPriceRange;
import com.preassignment.musinsa.domain.category.response.CategoryPriceResponse;
import com.preassignment.musinsa.domain.product.ProductCommendService;
import com.preassignment.musinsa.domain.product.ProductQueryService;
import com.preassignment.musinsa.domain.product.request.ProductRequest;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
private final ProductCommendService productCommendService;
private final ProductQueryService productQueryService;
private final RedisService redisService;

  private final long CACHE_TTL = 10; // 10분
  private final TimeUnit CACHE_TTL_UNIT = TimeUnit.MINUTES;

  public CategoryPriceResponse getLowestPricesByCategory() {
    String redisKey = "lowestPricesByCategory";
    if (redisService.hasKey(redisKey)) {
      return (CategoryPriceResponse) redisService.getValue(redisKey);
    }

    CategoryPriceResponse response = productQueryService.getLowestPricesByCategory();
    redisService.setValue(redisKey, response, CACHE_TTL, CACHE_TTL_UNIT);
    return response;
  }

  public BrandPriceResponse getLowestPricesByBrand() {
    String redisKey = "lowestPricesByBrand";
    if (redisService.hasKey(redisKey)) {
      return (BrandPriceResponse) redisService.getValue(redisKey);
    }

    BrandPriceResponse response = productQueryService.getLowestPricesByBrand();
    redisService.setValue(redisKey, response, CACHE_TTL, CACHE_TTL_UNIT);
    return response;
  }

  public CategoryPriceRange getPriceRangeByCategory(String categoryName) {
    String redisKey = "priceRangeByCategory:" + categoryName;
    if (redisService.hasKey(redisKey)) {
      return (CategoryPriceRange) redisService.getValue(redisKey);
    }

    CategoryPriceRange response = productQueryService.getPriceRangeByCategory(categoryName);
    redisService.setValue(redisKey, response, CACHE_TTL, CACHE_TTL_UNIT);
    return response;
  }

  public void addProduct(ProductRequest productRequest) {
    productCommendService.addProduct(productRequest);
  }

  public void updateProduct(Long id, ProductRequest productRequest) {
    productCommendService.updateProduct(id, productRequest);
  }

  public void deleteProduct(Long id) {
    productCommendService.deleteProduct(id);
  }
}
