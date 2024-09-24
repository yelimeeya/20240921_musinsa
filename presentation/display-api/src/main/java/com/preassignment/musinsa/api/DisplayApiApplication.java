package com.preassignment.musinsa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.preassignment.musinsa"})
@EnableJpaRepositories(basePackages = "com.preassignment.musinsa.database")
@EntityScan(basePackages = "com.preassignment.musinsa.database")
public class DisplayApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(DisplayApiApplication.class, args);
  }
}
