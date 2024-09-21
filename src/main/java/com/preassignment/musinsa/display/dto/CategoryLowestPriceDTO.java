package com.preassignment.musinsa.display.dto;

public class CategoryLowestPriceDTO {

  private String category;
  private String brand;
  private int price;

  public CategoryLowestPriceDTO(String category, String brand, int price) {
    this.category = category;
    this.brand = brand;
    this.price = price;
  }

  // Getters and Setters

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
