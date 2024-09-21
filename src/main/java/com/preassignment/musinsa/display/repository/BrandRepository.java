package com.preassignment.musinsa.display.repository;

import com.preassignment.musinsa.display.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
  // 기본적인 CRUD 메서드를 상속받음 (save, findById, deleteById 등)
}
