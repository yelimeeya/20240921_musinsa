package com.preassignment.musinsa.display.controller;

import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

  private final BrandService brandService;

  @Autowired
  public BrandController(BrandService brandService) {
    this.brandService = brandService;
  }

  // 브랜드 추가
  @PostMapping
  public Brand createBrand(@RequestBody Brand brand) {
    return brandService.saveBrand(brand);
  }

  // 브랜드 수정
  @PutMapping("/{id}")
  public Brand updateBrand(@PathVariable Long id, @RequestBody Brand updatedBrand) {
    return brandService.updateBrand(id, updatedBrand);
  }

  // 브랜드 삭제
  @DeleteMapping("/{id}")
  public void deleteBrand(@PathVariable Long id) {
    brandService.deleteBrand(id);
  }
}