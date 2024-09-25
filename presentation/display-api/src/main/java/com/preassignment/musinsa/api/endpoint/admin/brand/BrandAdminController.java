package com.preassignment.musinsa.api.endpoint.admin.brand;

import com.preassignment.musinsa.app.service.brand.BrandService;
import com.preassignment.musinsa.domain.brand.request.BrandRequest;
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
@RequestMapping("/private-api/brands")
public class BrandAdminController {

  private final BrandService brandService;

  @Autowired
  public BrandAdminController(BrandService brandService) {
    this.brandService = brandService;
  }

  // 브랜드 추가
  @PostMapping
  public ResponseEntity<String> addBrand(@RequestBody BrandRequest brandRequest) {
    brandService.addBrand(brandRequest);
    return ResponseEntity.ok("브랜드가 성공적으로 추가되었습니다.");
  }

  // 브랜드 수정
  @PutMapping("/{id}")
  public ResponseEntity<String> updateBrand(@PathVariable Long id,
      @RequestBody BrandRequest brandRequest) {
    brandService.updateBrand(id, brandRequest);
    return ResponseEntity.ok("브랜드가 성공적으로 수정되었습니다.");
  }

  // 브랜드 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBrand(@PathVariable Long id) {
    brandService.deleteBrand(id);
    return ResponseEntity.ok("브랜드가 성공적으로 삭제되었습니다.");
  }
}