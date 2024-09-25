package com.preassignment.musinsa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.preassignment.musinsa"})
@EntityScan(basePackages = "com.preassignment.musinsa.database")

public class DisplayApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(DisplayApiApplication.class, args);
  }
}
