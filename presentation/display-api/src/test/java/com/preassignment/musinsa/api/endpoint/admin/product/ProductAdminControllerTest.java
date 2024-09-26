package com.preassignment.musinsa.api.endpoint.admin.product;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preassignment.musinsa.app.service.product.ProductService;
import com.preassignment.musinsa.domain.product.request.ProductRequest;
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
@WebMvcTest(controllers = ProductAdminController.class)
public class ProductAdminControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("상품 추가 성공 테스트")
  void addProductTest() throws Exception {
    ProductRequest productRequest = new ProductRequest();
    productRequest.setCategory(1L);
    productRequest.setBrand(1L);
    productRequest.setPrice(10000);

    String productRequestJson = objectMapper.writeValueAsString(productRequest);

    mockMvc.perform(post("/private-api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productRequestJson))
        .andExpect(status().isOk());

    verify(productService, times(1)).addProduct(any(ProductRequest.class));
  }

  @Test
  @DisplayName("상품 수정 성공 테스트")
  void updateProductTest() throws Exception {
    ProductRequest productRequest = new ProductRequest();
    productRequest.setCategory(1L);
    productRequest.setBrand(1L);
    productRequest.setPrice(20000);

    String productRequestJson = objectMapper.writeValueAsString(productRequest);

    mockMvc.perform(put("/private-api/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(productRequestJson))
        .andExpect(status().isOk());

    verify(productService, times(1)).updateProduct(eq(1L), any(ProductRequest.class));
  }

  @Test
  @DisplayName("상품 삭제 성공 테스트")
  void deleteProductTest() throws Exception {
    mockMvc.perform(delete("/private-api/products/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(productService, times(1)).deleteProduct(eq(1L));
  }
}
