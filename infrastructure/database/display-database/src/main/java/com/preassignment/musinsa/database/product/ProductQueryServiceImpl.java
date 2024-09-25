package com.preassignment.musinsa.database.product;

import com.preassignment.musinsa.domain.brand.respose.BrandPriceResponse;
import com.preassignment.musinsa.domain.category.dto.CategoryPriceRange;
import com.preassignment.musinsa.domain.category.response.CategoryPriceResponse;
import com.preassignment.musinsa.domain.product.Product;
import com.preassignment.musinsa.domain.product.ProductQueryService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

  private final ProductRepository productRepository;

  @Override
  public CategoryPriceResponse getLowestPricesByCategory() {

    List<ProductEntity> products = productRepository.findAll();

    // 각 카테고리별 최저가 상품을 그룹화 후, 최저가 상품만 리스트로 변환
    List<Product> categoryLowestPriceList = products.stream()
        .map(ProductEntity::toDomain)
        .collect(Collectors.groupingBy(product -> product.getCategory().getName()))
        .values().stream()
        .map(productList -> productList.stream()
            .min(Comparator.comparingInt(Product::getPrice))
            .orElseThrow())
        .map(lowestProduct -> new Product(
            lowestProduct.getPrice(),
            lowestProduct.getBrand(),
            lowestProduct.getCategory()))
        .collect(Collectors.toList());

    // 총액 계산
    int totalPrice = categoryLowestPriceList.stream()
        .mapToInt(Product::getPrice).sum();

    return new CategoryPriceResponse(categoryLowestPriceList, String.format("%,d", totalPrice));
  }

  @Override
  public BrandPriceResponse getLowestPricesByBrand() {

    List<ProductEntity> products = productRepository.findAll();

    // 브랜드별로 카테고리의 상품들을 그룹화
    Map<String, Map<String, List<Product>>> brandToCategoryProducts = products.stream()
        .map(ProductEntity::toDomain).collect(
            Collectors.groupingBy(product -> product.getBrand().getName(),
                Collectors.groupingBy(product -> product.getCategory().getName())));

    String bestBrand = null;
    List<Product> bestCategoryPrices = new ArrayList<>();
    int lowestTotalPrice = Integer.MAX_VALUE;

    // 브랜드별로 모든 카테고리에서의 최저가 상품을 합산하여 총액을 계산
    for (Map.Entry<String, Map<String, List<Product>>> brandEntry : brandToCategoryProducts.entrySet()) {
      String brand = brandEntry.getKey();
      Map<String, List<Product>> categoryProducts = brandEntry.getValue();

      List<Product> categoryPrices = new ArrayList<>();
      int totalPrice = 0;
      boolean hasAllCategories = true;

      for (Map.Entry<String, List<Product>> categoryEntry : categoryProducts.entrySet()) {
        List<Product> productsInCategory = categoryEntry.getValue();

        // 해당 카테고리에서 최저가 상품을 찾음
        Product lowestProduct = productsInCategory.stream()
            .min(Comparator.comparingInt(Product::getPrice)).orElse(null);

        if (lowestProduct == null) {
          hasAllCategories = false;
          break;
        }

        // 카테고리별 최저가 상품을 추가하고, 가격 합산
        categoryPrices.add(new Product(lowestProduct.getPrice(), lowestProduct.getBrand(),
            lowestProduct.getCategory()));

        totalPrice += lowestProduct.getPrice();
      }

      // 모든 카테고리를 다룬 브랜드만 총액 비교
      if (hasAllCategories && totalPrice < lowestTotalPrice) {
        lowestTotalPrice = totalPrice;
        bestBrand = brand;
        bestCategoryPrices = categoryPrices;
      }
    }

    return new BrandPriceResponse(bestBrand, bestCategoryPrices,
        String.format("%,d", lowestTotalPrice));
  }

  @Override
  public CategoryPriceRange getPriceRangeByCategory(String categoryName) {

    // 카테고리 이름으로 상품 검색
    List<ProductEntity> products = productRepository.findByCategoryName(categoryName);

    if (products.isEmpty()) {
      throw new RuntimeException("해당 카테고리에는 상품이 없습니다.");
    }

    // 최저가 상품 찾기
    Product lowestProduct = products.stream().map(ProductEntity::toDomain)
        .min(Comparator.comparingInt(Product::getPrice))
        .orElseThrow(() -> new RuntimeException("최저가 상품을 찾을 수 없습니다."));

    // 최고가 상품 찾기
    Product highestProduct = products.stream().map(ProductEntity::toDomain)
        .max(Comparator.comparingInt(Product::getPrice))
        .orElseThrow(() -> new RuntimeException("최고가 상품을 찾을 수 없습니다."));

    // Product 생성 (최저가 상품)
    List<Product> lowestPriceList = List.of(
        new Product(lowestProduct.getPrice(), lowestProduct.getBrand(),
            lowestProduct.getCategory()));

    // Product 생성 (최고가 상품)
    List<Product> highestPriceList = List.of(
        new Product(highestProduct.getPrice(), highestProduct.getBrand(),
            highestProduct.getCategory()));

    return new CategoryPriceRange(categoryName, lowestPriceList, highestPriceList);
  }
}
