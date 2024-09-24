package com.preassignment.musinsa.domain.product;

import com.preassignment.musinsa.domain.product.request.ProductRequest;

public interface ProductCommendService {

  void addProduct(ProductRequest productRequest);

  void updateProduct(Long id, ProductRequest productRequest);

  void deleteProduct(Long id);
}
