package com.preassignment.musinsa.database.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.preassignment.musinsa")
@EntityScan(basePackages = "com.preassignment.musinsa")
public class h2DatabaseConfig {

}
