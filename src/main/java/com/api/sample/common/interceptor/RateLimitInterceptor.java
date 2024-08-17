package com.api.sample.common.interceptor;

import com.api.sample.common.tool.RateLimiter;
import com.api.sample.common.util.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {
    private final RateLimiter rateLimiter;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if(!uri.startsWith("/api/v1/")){
            return true;
        }
        String ip = HttpUtils.getClientIp();
        boolean isAllowed = rateLimiter.isAllowed(ip, 10, Duration.ofSeconds(1));
        if (!isAllowed) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
        }
        return true;
    }
}
