package com.preassignment.musinsa.cache.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;

  public void setValue(String key, Object value, long timeout, TimeUnit unit) {
    redisTemplate.opsForValue().set(key, value, timeout, unit);
  }

  public Object getValue(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public boolean hasKey(String key) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(key));
  }

  public void delValue(String key) {
    redisTemplate.delete(key);
  }

  public void delValuesByPattern(String pattern) {
    List<String> keysToDelete = redisTemplate.execute((RedisCallback<List<String>>) connection -> {
      List<String> keys = new ArrayList<>();
      ScanOptions options = ScanOptions.scanOptions().match(pattern).count(1000).build();
      Cursor<byte[]> cursor = connection.scan(options);
      cursor.forEachRemaining(key -> keys.add(new String(key)));
      return keys;
    });

    if (!keysToDelete.isEmpty()) {
      redisTemplate.delete(keysToDelete);
    }
  }
}