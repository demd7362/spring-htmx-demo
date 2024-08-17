package com.api.sample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ValueOperations<String, String> valueOps;
    public RedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.valueOps = redisTemplate.opsForValue();
    }


    public <T> T get(@NonNull String key, Class<T> clazz) {
        try {
            String value = valueOps.get(key);
            if (clazz == String.class) {
                return (T) value;
            }
            if (value == null) {
                return null;
            }
            return objectMapper.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize value from Redis", e);
        }
    }

    public void set(@NonNull String key, @NonNull Object value, Duration duration) {
        try {
            if (value.getClass() == String.class) {
                valueOps.set(key, (String) value, duration);
            } else {
                valueOps.set(key, objectMapper.writeValueAsString(value), duration);
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize value to Redis", e);
        }
    }

    public void set(@NonNull String key, @NonNull Object value) {
        try {
            if (value.getClass() == String.class) {
                valueOps.set(key, (String) value);
            } else {
                valueOps.set(key, objectMapper.writeValueAsString(value));
            }
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize value to Redis", e);
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
