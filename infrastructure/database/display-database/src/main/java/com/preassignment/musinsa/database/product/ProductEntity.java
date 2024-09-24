package com.preassignment.musinsa.database.product;

import com.preassignment.musinsa.database.brand.BrandEntity;
import com.preassignment.musinsa.database.category.CategoryEntity;
import com.preassignment.musinsa.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "product")
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private int price;
  @JoinColumn
  @ManyToOne
  private BrandEntity brand;
  @JoinColumn
  @ManyToOne
  private CategoryEntity category;

  public static ProductEntity toEntity(Product product) {
    ProductEntity entity = new ProductEntity();
    entity.setPrice(product.getPrice());
    entity.setBrand(product.getBrand() != null ? BrandEntity.toEntity(product.getBrand())
        : null);
    entity.setCategory(
        product.getCategory() != null ? CategoryEntity.toEntity(product.getCategory())
            : null);
    return entity;
  }

  public Product toDomain() {
    return new Product(
        this.price,
        this.brand != null ? this.brand.toDomain() : null,
        this.category != null ? this.category.toDomain() : null
    );
  }
}