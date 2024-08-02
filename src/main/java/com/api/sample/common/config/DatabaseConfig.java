package com.api.sample.common.config;

import com.api.sample.common.model.DatabaseSecret;
import com.api.sample.common.security.PrincipalDetails;
import com.api.sample.entity.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;

@Configuration
@EnableJpaRepositories(basePackages = "com.api.sample.repository")
@EnableJpaAuditing
@RequiredArgsConstructor
public class DatabaseConfig {
    @Bean
    @DependsOn("databaseSecret")
    public DataSource dataSource(DatabaseSecret databaseSecret) throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseSecret.getJdbcUrl());
        hikariConfig.setUsername(databaseSecret.getUsername());
        hikariConfig.setPassword(databaseSecret.getPassword());
        hikariConfig.setDriverClassName(databaseSecret.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }


    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                return Optional.empty();
            }
            Object principal = authentication.getPrincipal();
            if(principal instanceof PrincipalDetails principalDetails) {
                return Optional.of(principalDetails.user());
            }
            return Optional.empty();
        };
    }
}
