package com.api.sample.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class HttpUtils {
    public static HttpServletRequest getRequest() {
        return getAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getAttributes().getResponse();
    }

    private static ServletRequestAttributes getAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }


    @SneakyThrows
    public static String getClientIp() {
        HttpServletRequest request = getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasText(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                InetAddress inetAddress = InetAddress.getLocalHost();
                ipAddress = inetAddress.getHostAddress();
            }
        }
        if (ipAddress != null && ipAddress.length() > 15 && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        return ipAddress;
    }

    public static Map<String, String> getHeaders() {
        HttpServletRequest request = getRequest();
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }
        return headers;
    }

    public static boolean hasAuthorizationHeader() {
        HttpServletRequest request = getRequest();
        String authHeader = request.getHeader("Authorization");
        return !authHeader.isBlank();
    }

    public static Map<String, String[]> getParameterMap() {
        HttpServletRequest request = getRequest();
        return request.getParameterMap();
    }


}
