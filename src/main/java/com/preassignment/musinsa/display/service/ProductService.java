package com.preassignment.musinsa.display.service;

import com.preassignment.musinsa.display.dto.BrandPriceResponseDTO;
import com.preassignment.musinsa.display.dto.CategoryPriceRangeDTO;
import com.preassignment.musinsa.display.dto.CategoryPriceResponseDTO;
import com.preassignment.musinsa.display.dto.ProductDTO;
import com.preassignment.musinsa.display.dto.ProductRequestDTO;
import com.preassignment.musinsa.display.entity.Brand;
import com.preassignment.musinsa.display.entity.Category;
import com.preassignment.musinsa.display.entity.Product;
import com.preassignment.musinsa.display.repository.BrandRepository;
import com.preassignment.musinsa.display.repository.CategoryRepository;
import com.preassignment.musinsa.display.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final BrandRepository brandRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, BrandRepository brandRepository,
      CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.brandRepository = brandRepository;
    this.categoryRepository = categoryRepository;
  }

  // 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회
  public CategoryPriceResponseDTO getLowestPricesByCategory() {
    List<Product> products = productRepository.findAll();

    List<ProductDTO> categoryLowestPriceList = new ArrayList<>(products.stream()
        .collect(Collectors.groupingBy(Product::getCategory,
            Collectors.collectingAndThen(
                Collectors.toList(),
                productList -> {
                  Product lowestPriceProduct = productList.stream()
                      .min(Comparator.comparingInt(Product::getPrice))
                      .orElseThrow();
                  return new ProductDTO(
                      lowestPriceProduct.getCategory().getName(),
                      lowestPriceProduct.getBrand().getName(),
                      String.format("%,d", lowestPriceProduct.getPrice()));
                }
            )))
        .values());

    int totalPrice = categoryLowestPriceList.stream()
        .mapToInt(lowestPriceDTO -> {
          String priceWithoutCommas = lowestPriceDTO.getPrice().replace(",", "");
          return Integer.parseInt(priceWithoutCommas);
        })
        .sum();

    return new CategoryPriceResponseDTO(categoryLowestPriceList,
        String.format("%,d", totalPrice));
  }


  // 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회
  public BrandPriceResponseDTO getLowestPricesByBrand() {

    List<Product> products = productRepository.findAll();

    // 브랜드별로 카테고리의 상품들을 그룹화
    Map<String, Map<Category, List<Product>>> brandToCategoryProducts = products.stream()
        .collect(Collectors.groupingBy(product -> product.getBrand().getName(),
            Collectors.groupingBy(Product::getCategory)));

    String bestBrand = null;
    List<ProductDTO> bestCategoryPrices = new ArrayList<>();
    int lowestTotalPrice = Integer.MAX_VALUE;

    // 브랜드별로 모든 카테고리에서의 최저가 상품을 합산하여 총액을 계산
    for (Map.Entry<String, Map<Category, List<Product>>> brandEntry : brandToCategoryProducts.entrySet()) {
      String brand = brandEntry.getKey();
      Map<Category, List<Product>> categoryProducts = brandEntry.getValue();

      // 각 브랜드가 모든 카테고리를 다루고 있는지 확인하고, 카테고리별 최저가를 합산
      List<ProductDTO> categoryPrices = new ArrayList<>();
      int totalPrice = 0;
      boolean allCategoriesCovered = true;

      for (Map.Entry<Category, List<Product>> categoryEntry : categoryProducts.entrySet()) {
        List<Product> productsInCategory = categoryEntry.getValue();

        // 해당 카테고리에서 최저가 상품을 찾음
        Product lowestProduct = productsInCategory.stream()
            .min(Comparator.comparingInt(Product::getPrice))
            .orElse(null);

        if (lowestProduct == null) {
          allCategoriesCovered = false;
          break;
        }

        // 카테고리별 최저가 상품을 추가하고, 가격 합산
        categoryPrices.add(new ProductDTO(
            lowestProduct.getCategory().getName(),
            lowestProduct.getBrand().getName(),
            String.format("%,d", lowestProduct.getPrice())
        ));

        totalPrice += lowestProduct.getPrice();
      }

      // 모든 카테고리를 다룬 브랜드만 총액 비교
      if (allCategoriesCovered && totalPrice < lowestTotalPrice) {
        lowestTotalPrice = totalPrice;
        bestBrand = brand;
        bestCategoryPrices = categoryPrices;
      }
    }

    String formattedTotalPrice = String.format("%,d", lowestTotalPrice);
    return new BrandPriceResponseDTO(bestBrand, bestCategoryPrices, formattedTotalPrice);
  }


  // 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회
  public CategoryPriceRangeDTO getPriceRangeByCategory(String categoryName) {
    // 카테고리 이름으로 상품 검색
    List<Product> products = productRepository.findByCategoryName(categoryName);

    if (products.isEmpty()) {
      throw new RuntimeException("해당 카테고리에는 상품이 없습니다.");
    }

    // 최저가 상품 찾기
    Product lowestProduct = products.stream()
        .min(Comparator.comparingInt(Product::getPrice))
        .orElseThrow(() -> new RuntimeException("최저가 상품을 찾을 수 없습니다."));

    // 최고가 상품 찾기
    Product highestProduct = products.stream()
        .max(Comparator.comparingInt(Product::getPrice))
        .orElseThrow(() -> new RuntimeException("최고가 상품을 찾을 수 없습니다."));

    // ProductDTO 생성 (최저가 상품)
    List<ProductDTO> lowestPriceList = List.of(new ProductDTO(
        lowestProduct.getCategory().getName(),
        lowestProduct.getBrand().getName(),
        String.format("%,d", lowestProduct.getPrice())
    ));

    // ProductDTO 생성 (최고가 상품)
    List<ProductDTO> highestPriceList = List.of(new ProductDTO(
        highestProduct.getCategory().getName(),
        highestProduct.getBrand().getName(),
        String.format("%,d", highestProduct.getPrice())
    ));

    return new CategoryPriceRangeDTO(categoryName, lowestPriceList, highestPriceList);
  }

  // 상품 추가
  public void addProduct(ProductRequestDTO productRequest) {
    // 카테고리와 브랜드 조회
    Category category = categoryRepository.findById(productRequest.getCategory())
        .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

    Brand brand = brandRepository.findById(productRequest.getBrand())
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    Product product = new Product(productRequest.getPrice(), brand, category);
    productRepository.save(product);
  }

  // 상품 수정
  public void updateProduct(Long id, ProductRequestDTO productRequest) {
    // 상품 존재 여부 확인
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

    // 카테고리와 브랜드 조회
    Category category = categoryRepository.findById(productRequest.getCategory())
        .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

    Brand brand = brandRepository.findById(productRequest.getBrand())
        .orElseThrow(() -> new RuntimeException("해당 브랜드를 찾을 수 없습니다."));

    product.setCategory(category);
    product.setBrand(brand);
    product.setPrice(productRequest.getPrice());

    productRepository.save(product);
  }

  // 상품 삭제
  public void deleteProduct(Long id) {
    // 상품 존재 여부 확인
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

    productRepository.delete(product);
  }
}

