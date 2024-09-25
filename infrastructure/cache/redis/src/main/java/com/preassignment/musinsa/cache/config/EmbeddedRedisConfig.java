package com.preassignment.musinsa.cache.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.File;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

@Configuration
public class EmbeddedRedisConfig {

  private RedisServer redisServer;

  @Value("${spring.redis.port}")
  private int redisPort;

  @PostConstruct
  public void initEmbeddedRedis() {
    if (isArmMac()) {
      redisServer = new RedisServer(Objects.requireNonNull(getRedisFile()), redisPort);
    } else {
      redisServer = RedisServer.builder()
          .port(redisPort)
          .setting("maxmemory 128M")
          .build();
    }
    redisServer.start();
  }

  @PreDestroy
  public void destroyEmbeddedRedis() {
    redisServer.stop();
  }

  private boolean isArmMac() {
    return System.getProperty("os.arch").contains("aarch64") &&
        System.getProperty("os.name").contains("Mac OS X");
  }

  private File getRedisFile() {
    return new File("./infrastructure/cache/redis/src/main/resources/binary/redis-server");
  }
}
