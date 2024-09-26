package com.preassignment.musinsa.api.endpoint.admin.brand;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.preassignment.musinsa.app.service.brand.BrandService;
import com.preassignment.musinsa.domain.brand.request.BrandRequest;
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
@WebMvcTest(controllers = BrandAdminController.class)
public class BrandAdminControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BrandService brandService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("브랜드 추가 성공 테스트")
  void addBrandTest() throws Exception {
    BrandRequest brandRequest = new BrandRequest();
    brandRequest.setName("NewB");

    String brandRequestJson = objectMapper.writeValueAsString(brandRequest);

    mockMvc.perform(post("/private-api/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .content(brandRequestJson))
        .andExpect(status().isOk());

    verify(brandService, times(1)).addBrand(any(BrandRequest.class));
  }

  @Test
  @DisplayName("브랜드 수정 성공 테스트")
  void updateBrandTest() throws Exception {
    BrandRequest brandRequest = new BrandRequest();
    brandRequest.setName("UpdatedB");

    String brandRequestJson = objectMapper.writeValueAsString(brandRequest);

    mockMvc.perform(put("/private-api/brands/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(brandRequestJson))
        .andExpect(status().isOk());

    verify(brandService, times(1)).updateBrand(eq(1L), any(BrandRequest.class));
  }

  @Test
  @DisplayName("브랜드 삭제 성공 테스트")
  void deleteBrandTest() throws Exception {
    mockMvc.perform(delete("/private-api/brands/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(brandService, times(1)).deleteBrand(eq(1L));
  }
}
