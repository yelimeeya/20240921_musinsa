package com.preassignment.musinsa.display.service;

import com.preassignment.musinsa.display.dto.BrandRequestDTO;
import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.repository.BrandRepository;
import com.preassignment.musinsa.display.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BrandServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private BrandRepository brandRepository;

  @InjectMocks
  private BrandService brandService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAddBrand() {
    BrandRequestDTO brandRequest = new BrandRequestDTO();
    brandRequest.setName("New Brand");

    Brand brand = new Brand(brandRequest.getName());

    when(brandRepository.save(any(Brand.class))).thenReturn(brand);

    brandService.addBrand(brandRequest);

    verify(brandRepository, times(1)).save(any(Brand.class));
  }

  @Test
  public void testUpdateBrand_ProductCountValidationFail() {
    Brand brand = new Brand("Existing Brand");
    when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
    when(productRepository.countByBrand(brand)).thenReturn(1L);

    BrandRequestDTO brandRequest = new BrandRequestDTO();
    brandRequest.setName("Updated Brand");

    assertThrows(RuntimeException.class, () -> brandService.updateBrand(1L, brandRequest));
  }

  @Test
  public void testDeleteBrand_ProductCountValidationFail() {
    Brand brand = new Brand("Existing Brand");
    when(brandRepository.findById(anyLong())).thenReturn(Optional.of(brand));
    when(productRepository.countByBrand(brand)).thenReturn(1L);

    assertThrows(RuntimeException.class, () -> brandService.deleteBrand(1L));
  }
}
