package com.api.sample.common.config;

import com.api.sample.common.model.DatabaseSecret;
import com.api.sample.common.model.OAuth2Secret;
import com.api.sample.common.tool.SystemEnvironment;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class AppConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public DatabaseSecret databaseSecret() throws IOException {
        Path path;
        if(SystemEnvironment.isWindows()){
            path = Path.of("C:\\database_secret.json");
        } else if(SystemEnvironment.isLinux()){
            path = Path.of("etc/database_secret.json");
        } else {
            throw new RuntimeException("Unsupported OS");
        }
        String json = Files.readString(path);
        return objectMapper.readValue(json, DatabaseSecret.class);
    }
    @Bean
    public OAuth2Secret oAuth2Secret() throws IOException {
        Path path;
        if(SystemEnvironment.isWindows()){
            path = Path.of("C:\\oauth2_secret.json");
        } else if(SystemEnvironment.isLinux()){
            path = Path.of("etc/oauth2_secret.json");
        } else {
            throw new RuntimeException("Unsupported OS");
        }
        String json = Files.readString(path);
        return objectMapper.readValue(json, OAuth2Secret.class);
    }
}
