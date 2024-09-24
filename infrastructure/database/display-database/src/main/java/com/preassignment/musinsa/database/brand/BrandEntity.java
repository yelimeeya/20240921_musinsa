package com.preassignment.musinsa.database.brand;

import com.preassignment.musinsa.domain.brand.Brand;
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
@Table(name = "brand")
public class BrandEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public Brand toDomain() {
    return new Brand(this.name);
  }

  public static BrandEntity toEntity(Brand brand) {
    BrandEntity entity = new BrandEntity();
    entity.setName(brand.getName());
    return entity;
  }
}
