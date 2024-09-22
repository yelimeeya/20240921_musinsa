package com.preassignment.musinsa.display.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.preassignment.musinsa.display.dto.ProductRequestDTO;
import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.entity.Category;
import com.preassignment.musinsa.display.entity.Product;
import com.preassignment.musinsa.display.repository.BrandRepository;
import com.preassignment.musinsa.display.repository.CategoryRepository;
import com.preassignment.musinsa.display.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private BrandRepository brandRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testAddProduct_Success() {
    ProductRequestDTO productRequest = new ProductRequestDTO(1L, 1L, 10000);

    Category mockCategory = new Category("상의");
    Brand mockBrand = new Brand("Brand A");
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
    when(brandRepository.findById(1L)).thenReturn(Optional.of(mockBrand));

    productService.addProduct(productRequest);

    verify(productRepository, times(1)).save(any(Product.class));
  }

  @Test
  public void testAddProduct_CategoryNotFound() {
    ProductRequestDTO productRequest = new ProductRequestDTO(1L, 1L, 10000);

    when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.addProduct(productRequest);
    });

    assertEquals("해당 카테고리를 찾을 수 없습니다.", exception.getMessage());
    verify(productRepository, never()).save(any(Product.class));
  }

  @Test
  public void testAddProduct_BrandNotFound() {
    ProductRequestDTO productRequest = new ProductRequestDTO(1L, 1L, 10000);

    Category mockCategory = new Category("상의");
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
    when(brandRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.addProduct(productRequest);
    });

    assertEquals("해당 브랜드를 찾을 수 없습니다.", exception.getMessage());
    verify(productRepository, never()).save(any(Product.class));
  }

  @Test
  public void testUpdateProduct_Success() {
    ProductRequestDTO productRequest = new ProductRequestDTO(1L, 1L, 12000);
    Product existingProduct = new Product(10000, new Brand("Brand A"), new Category("상의"));

    when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category("상의")));
    when(brandRepository.findById(1L)).thenReturn(Optional.of(new Brand("Brand A")));
    when(productRepository.countByCategoryAndBrand(any(Category.class),
        any(Brand.class))).thenReturn(2L);

    productService.updateProduct(1L, productRequest);

    verify(productRepository, times(1)).save(existingProduct);
  }

  @Test
  public void testUpdateProduct_ProductNotFound() {
    ProductRequestDTO productRequest = new ProductRequestDTO(1L, 1L, 12000);

    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.updateProduct(1L, productRequest);
    });

    assertEquals("해당 상품을 찾을 수 없습니다.", exception.getMessage());
    verify(productRepository, never()).save(any(Product.class));
  }

  @Test
  public void testDeleteProduct_Success() {
    Product product = new Product(10000, new Brand("Brand A"), new Category("상의"));

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(productRepository.countByCategoryAndBrand(any(Category.class),
        any(Brand.class))).thenReturn(2L);

    productService.deleteProduct(1L);

    verify(productRepository, times(1)).delete(product);
  }

  @Test
  public void testDeleteProduct_ProductNotFound() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.deleteProduct(1L);
    });

    assertEquals("해당 상품을 찾을 수 없습니다.", exception.getMessage());
    verify(productRepository, never()).delete(any(Product.class));
  }

  @Test
  public void testDeleteProduct_LastProductInCategory() {
    Product product = new Product(10000, new Brand("Brand A"), new Category("상의"));

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(productRepository.countByCategoryAndBrand(any(Category.class),
        any(Brand.class))).thenReturn(1L);

    Exception exception = assertThrows(RuntimeException.class, () -> {
      productService.deleteProduct(1L);
    });

    assertEquals("브랜드의 카테고리에는 최소 하나의 상품이 존재해야 합니다.", exception.getMessage());
    verify(productRepository, never()).delete(any(Product.class));
  }
}
