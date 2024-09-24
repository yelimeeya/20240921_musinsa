package com.preassignment.musinsa.database.product;

import com.preassignment.musinsa.database.brand.BrandEntity;
import com.preassignment.musinsa.database.brand.BrandRepository;
import com.preassignment.musinsa.database.category.CategoryEntity;
import com.preassignment.musinsa.database.category.CategoryRepository;
import com.preassignment.musinsa.domain.product.ProductCommendService;
import com.preassignment.musinsa.domain.product.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommendServiceImpl implements ProductCommendService {

  private final CategoryRepository categoryRepository;
  private final BrandRepository brandRepository;
  private final ProductRepository productRepository;

  @Override
  public void addProduct(ProductRequest productRequest) {
    // 카테고리와 브랜드 조회
    CategoryEntity categoryEntity = categoryRepository.findById(productRequest.getCategory())
        .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

    BrandEntity brandEntity = brandRepository.findById(productRequest.getBrand())
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    ProductEntity productEntity = new ProductEntity();
    productEntity.setPrice(productRequest.getPrice());
    productEntity.setBrand(brandEntity);
    productEntity.setCategory(categoryEntity);
    productRepository.save(productEntity);
  }

  @Override
  public void updateProduct(Long id, ProductRequest productRequest) {
    // 상품 존재 여부 확인
    ProductEntity productEntity = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

    // 카테고리 존재 여부 확인
    CategoryEntity categoryEntity = categoryRepository.findById(productRequest.getCategory())
        .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

    // 브랜드 존재 여부 확인
    BrandEntity brandEntity = brandRepository.findById(productRequest.getBrand())
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    // 브랜드의 카테고리에 최소 하나의 상품이 남는지 확인
    if (productRepository.countByCategoryAndBrand(productEntity.getCategory(),
        productEntity.getBrand()) == 1) {
      throw new RuntimeException("브랜드의 카테고리에는 최소 하나의 상품이 존재해야 합니다.");
    }

    productEntity.setPrice(productRequest.getPrice());
    productEntity.setCategory(categoryEntity);
    productEntity.setBrand(brandEntity);
    productRepository.save(productEntity);
  }

  @Override
  public void deleteProduct(Long id) {
    // 상품 존재 여부 확인
    ProductEntity productEntity = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

    CategoryEntity categoryEntity = productEntity.getCategory();
    BrandEntity brandEntity = productEntity.getBrand();
    // 브랜드의 카테고리에 최소 하나의 상품이 남는지 확인
    if (productRepository.countByCategoryAndBrand(categoryEntity, brandEntity) == 1) {
      throw new RuntimeException("브랜드의 카테고리에는 최소 하나의 상품이 존재해야 합니다.");
    }

    productRepository.delete(productEntity);
  }
}
