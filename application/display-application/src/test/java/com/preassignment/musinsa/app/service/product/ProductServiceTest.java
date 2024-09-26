package com.preassignment.musinsa.app.service.product;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.preassignment.musinsa.cache.service.RedisService;
import com.preassignment.musinsa.domain.product.ProductCommendService;
import com.preassignment.musinsa.domain.product.ProductQueryService;
import com.preassignment.musinsa.domain.product.request.ProductRequest;
import com.preassignment.musinsa.domain.brand.respose.BrandPriceResponse;
import com.preassignment.musinsa.domain.category.dto.CategoryPriceRange;
import com.preassignment.musinsa.domain.category.response.CategoryPriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

  @Test
  @DisplayName("카테고리별 최저가 조회 성공 테스트")
  void getLowestPricesByCategorySuccessTest() {
    ProductQueryService productQueryService = mock(ProductQueryService.class);
    RedisService redisService = mock(RedisService.class);
    ProductCommendService productCommendService = mock(ProductCommendService.class);
    ProductService productService = new ProductService(productCommendService, productQueryService, redisService);

    CategoryPriceResponse response = new CategoryPriceResponse();
    when(redisService.hasKey("lowestPricesByCategory")).thenReturn(false);
    when(productQueryService.getLowestPricesByCategory()).thenReturn(response);

    productService.getLowestPricesByCategory();

    verify(redisService, times(1)).setValue(anyString(), any(CategoryPriceResponse.class), anyLong(), any());
  }

  @Test
  @DisplayName("브랜드별 최저가 조회 성공 테스트")
  void getLowestPricesByBrandSuccessTest() {
    ProductQueryService productQueryService = mock(ProductQueryService.class);
    RedisService redisService = mock(RedisService.class);
    ProductCommendService productCommendService = mock(ProductCommendService.class);
    ProductService productService = new ProductService(productCommendService, productQueryService, redisService);

    BrandPriceResponse response = new BrandPriceResponse();
    when(redisService.hasKey("lowestPricesByBrand")).thenReturn(false);
    when(productQueryService.getLowestPricesByBrand()).thenReturn(response);

    productService.getLowestPricesByBrand();

    verify(redisService, times(1)).setValue(anyString(), any(BrandPriceResponse.class), anyLong(), any());
  }

  @Test
  @DisplayName("카테고리 가격 범위 조회 성공 테스트")
  void getPriceRangeByCategorySuccessTest() {
    ProductQueryService productQueryService = mock(ProductQueryService.class);
    RedisService redisService = mock(RedisService.class);
    ProductCommendService productCommendService = mock(ProductCommendService.class);
    ProductService productService = new ProductService(productCommendService, productQueryService, redisService);

    CategoryPriceRange response = new CategoryPriceRange();
    String categoryName = "CategoryA";
    when(redisService.hasKey("priceRangeByCategory:" + categoryName)).thenReturn(false);
    when(productQueryService.getPriceRangeByCategory(categoryName)).thenReturn(response);

    productService.getPriceRangeByCategory(categoryName);

    verify(redisService, times(1)).setValue(anyString(), any(CategoryPriceRange.class), anyLong(), any());
  }

  @Test
  @DisplayName("상품 추가 성공 테스트")
  void addProductSuccessTest() {
    ProductCommendService productCommendService = mock(ProductCommendService.class);
    RedisService redisService = mock(RedisService.class);
    ProductQueryService productQueryService = mock(ProductQueryService.class);
    ProductService productService = new ProductService(productCommendService, productQueryService, redisService);

    ProductRequest productRequest = new ProductRequest();
    productRequest.setCategory(1L);
    productRequest.setBrand(1L);
    productRequest.setPrice(10000);

    productService.addProduct(productRequest);

    verify(productCommendService, times(1)).addProduct(any(ProductRequest.class));
    verify(redisService, times(1)).delValue("lowestPricesByCategory");
    verify(redisService, times(1)).delValue("lowestPricesByBrand");
    verify(redisService, times(1)).delValuesByPattern("priceRangeByCategory:*");
  }

  @Test
  @DisplayName("상품 추가 실패 테스트")
  void addProductFailureTest() {
    ProductCommendService productCommendService = mock(ProductCommendService.class);
    RedisService redisService = mock(RedisService.class);
    ProductQueryService productQueryService = mock(ProductQueryService.class);
    doThrow(RuntimeException.class).when(productCommendService).addProduct(any(ProductRequest.class));
    ProductService productService = new ProductService(productCommendService, productQueryService, redisService);

    ProductRequest productRequest = new ProductRequest();
    productRequest.setCategory(1L);
    productRequest.setBrand(1L);
    productRequest.setPrice(10000);

    assertThrows(RuntimeException.class, () -> productService.addProduct(productRequest));

    verify(redisService, times(0)).delValue("lowestPricesByCategory");
    verify(redisService, times(0)).delValue("lowestPricesByBrand");
    verify(redisService, times(0)).delValuesByPattern("priceRangeByCategory:*");
  }

  @Test
  @DisplayName("상품 수정 성공 테스트")
  void updateProductSuccessTest() {
    ProductCommendService productCommendService = mock(ProductCommendService.class);
    RedisService redisService = mock(RedisService.class);
    ProductQueryService productQueryService = mock(ProductQueryService.class);
    ProductService productService = new ProductService(productCommendService, productQueryService, redisService);

    Long productId = 1L;
    ProductRequest productRequest = new ProductRequest();
    productRequest.setCategory(1L);
    productRequest.setBrand(1L);
    productRequest.setPrice(20000);

    productService.updateProduct(productId, productRequest);

    verify(productCommendService, times(1)).updateProduct(eq(productId), any(ProductRequest.class));
    verify(redisService, times(1)).delValue("lowestPricesByCategory");
    verify(redisService, times(1)).delValue("lowestPricesByBrand");
    verify(redisService, times(1)).delValuesByPattern("priceRangeByCategory:*");
  }

  @Test
  @DisplayName("상품 삭제 성공 테스트")
  void deleteProductSuccessTest() {
    ProductCommendService productCommendService = mock(ProductCommendService.class);
    RedisService redisService = mock(RedisService.class);
    ProductQueryService productQueryService = mock(ProductQueryService.class);
    ProductService productService = new ProductService(productCommendService, productQueryService, redisService);

    Long productId = 1L;
    productService.deleteProduct(productId);

    verify(productCommendService, times(1)).deleteProduct(productId);
    verify(redisService, times(1)).delValue("lowestPricesByCategory");
    verify(redisService, times(1)).delValue("lowestPricesByBrand");
    verify(redisService, times(1)).delValuesByPattern("priceRangeByCategory:*");
  }
}
