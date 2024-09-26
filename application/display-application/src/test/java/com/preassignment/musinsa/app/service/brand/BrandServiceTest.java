package com.preassignment.musinsa.app.service.brand;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.preassignment.musinsa.cache.service.RedisService;
import com.preassignment.musinsa.domain.brand.BrandCommendService;
import com.preassignment.musinsa.domain.brand.request.BrandRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BrandServiceTest {

  @Test
  @DisplayName("브랜드 추가 성공 테스트")
  void addBrandSuccessTest() {
    BrandCommendService brandCommendService = mock(BrandCommendService.class);
    RedisService redisService = mock(RedisService.class);
    BrandService brandService = new BrandService(brandCommendService, redisService);

    BrandRequest brandRequest = new BrandRequest();
    brandRequest.setName("NewB");

    brandService.addBrand(brandRequest);

    verify(brandCommendService, times(1)).addBrand(any(BrandRequest.class));
    verify(redisService, times(1)).delValue("lowestPricesByCategory");
    verify(redisService, times(1)).delValue("lowestPricesByBrand");
    verify(redisService, times(1)).delValuesByPattern("priceRangeByCategory:*");
  }

  @Test
  @DisplayName("브랜드 수정 성공 테스트")
  void updateBrandSuccessTest() {
    BrandCommendService brandCommendService = mock(BrandCommendService.class);
    RedisService redisService = mock(RedisService.class);
    BrandService brandService = new BrandService(brandCommendService, redisService);

    Long brandId = 1L;
    BrandRequest brandRequest = new BrandRequest();
    brandRequest.setName("UpdateB");

    brandService.updateBrand(brandId, brandRequest);

    verify(brandCommendService, times(1)).updateBrand(eq(brandId), any(BrandRequest.class));
    verify(redisService, times(1)).delValue("lowestPricesByCategory");
    verify(redisService, times(1)).delValue("lowestPricesByBrand");
    verify(redisService, times(1)).delValuesByPattern("priceRangeByCategory:*");
  }


  @Test
  @DisplayName("브랜드 삭제 성공 테스트")
  void deleteBrandSuccessTest() {
    BrandCommendService brandCommendService = mock(BrandCommendService.class);
    RedisService redisService = mock(RedisService.class);
    BrandService brandService = new BrandService(brandCommendService, redisService);

    Long brandId = 1L;
    brandService.deleteBrand(brandId);

    verify(brandCommendService, times(1)).deleteBrand(brandId);
    verify(redisService, times(1)).delValue("lowestPricesByCategory");
    verify(redisService, times(1)).delValue("lowestPricesByBrand");
    verify(redisService, times(1)).delValuesByPattern("priceRangeByCategory:*");
  }

  @Test
  @DisplayName("브랜드 추가 실패 테스트")
  void addBrandFailureTest() {
    BrandCommendService brandCommendService = mock(BrandCommendService.class);
    RedisService redisService = mock(RedisService.class);
    doThrow(RuntimeException.class).when(brandCommendService).addBrand(any(BrandRequest.class));
    BrandService brandService = new BrandService(brandCommendService, redisService);

    BrandRequest brandRequest = new BrandRequest();
    brandRequest.setName("NewB");

    assertThrows(RuntimeException.class, () -> brandService.addBrand(brandRequest));

    verify(redisService, times(0)).delValue("lowestPricesByCategory");
    verify(redisService, times(0)).delValue("lowestPricesByBrand");
    verify(redisService, times(0)).delValuesByPattern("priceRangeByCategory:*");
  }
}
