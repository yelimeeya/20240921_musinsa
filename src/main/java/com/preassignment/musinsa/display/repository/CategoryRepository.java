package com.preassignment.musinsa.display.repository;

import com.preassignment.musinsa.display.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  // 카테고리 이름으로 카테고리 찾기
  Optional<Category> findByName(String name);
}
