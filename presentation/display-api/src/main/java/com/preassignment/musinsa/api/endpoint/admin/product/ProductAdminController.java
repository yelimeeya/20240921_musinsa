package com.preassignment.musinsa.api.endpoint.admin.product;

import com.preassignment.musinsa.app.service.product.ProductService;
import com.preassignment.musinsa.domain.product.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private-api/products")
public class ProductAdminController {

  private final ProductService productService;

  @Autowired
  public ProductAdminController(ProductService productService) {
    this.productService = productService;
  }

  // 상품 추가
  @PostMapping
  public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
    productService.addProduct(productRequest);
    return ResponseEntity.ok("상품이 성공적으로 추가되었습니다.");
  }

  // 상품 수정
  @PutMapping("/{id}")
  public ResponseEntity<String> updateProduct(@PathVariable Long id,
      @RequestBody ProductRequest productRequest) {
    productService.updateProduct(id, productRequest);
    return ResponseEntity.ok("상품이 성공적으로 수정되었습니다.");
  }

  // 상품 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
  }
}
