package com.preassignment.musinsa.api.endpoint.display.product;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preassignment.musinsa.app.service.product.ProductService;
import com.preassignment.musinsa.domain.brand.respose.BrandPriceResponse;
import com.preassignment.musinsa.domain.category.dto.CategoryPriceRange;
import com.preassignment.musinsa.domain.category.response.CategoryPriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Test
  @DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회 성공 테스트")
  void getLowestPricesByCategoryTest() throws Exception {
    CategoryPriceResponse mockResponse = new CategoryPriceResponse();
    when(productService.getLowestPricesByCategory()).thenReturn(mockResponse);

    mockMvc.perform(get("/api/products/category/lowest-prices")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회 성공 테스트")
  void getLowestPricesByBrandTest() throws Exception {
    BrandPriceResponse mockResponse = new BrandPriceResponse();
    when(productService.getLowestPricesByBrand()).thenReturn(mockResponse);

    mockMvc.perform(get("/api/products/brand/lowest-prices")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회 성공 테스트")
  void getPriceRangeByCategoryTest() throws Exception {
    String categoryName = "모자";
    CategoryPriceRange mockResponse = new CategoryPriceRange();
    when(productService.getPriceRangeByCategory(anyString())).thenReturn(mockResponse);

    mockMvc.perform(get("/api/products/category/price-range")
            .param("categoryName", categoryName)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}