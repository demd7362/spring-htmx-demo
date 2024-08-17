package com.api.sample.common.tool;

import com.api.sample.common.constant.Constants;
import com.api.sample.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimiter {
    private final RedisService redisService;

    public boolean isAllowed(String key, int maxRequests, Duration duration) {
        Integer count = redisService.get(getKey(key), Integer.class);
        if (count == null) {
            redisService.set(getKey(key), 1, duration);
            return true;
        }
        if (count < maxRequests) {
            redisService.set(getKey(key), count + 1, duration);
            return true;
        }
        return false;
    }

    private String getKey(String key) {
        return Constants.Redis.RATE_LIMITER_KEY + key;
    }
}
