package com.preassignment.musinsa.database.category;

import com.preassignment.musinsa.domain.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public static CategoryEntity toEntity(Category category) {
    CategoryEntity entity = new CategoryEntity();
    entity.setName(category.getName());
    return entity;
  }

  public Category toDomain() {
    return new Category(this.name);
  }
}
