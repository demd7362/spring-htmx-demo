package com.api.sample.common.config;

import com.api.sample.common.tool.SystemEnvironment;
import com.api.sample.http.subway.SubwayHttpInterface;
import com.api.sample.model.SecretConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class AppConfig {
    private final ObjectMapper objectMapper;
    private static final String BASE_URL = "https://api.odcloud.kr/api";
    private static final String AUTHORIZATION_PREFIX = "Infuser ";

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders httpHeaders = request.getHeaders();
            httpHeaders.forEach((key, value) -> {
                log.info("Request header key : {} value : {}", key, value);
            });
            long start = System.currentTimeMillis();
            ClientHttpResponse response = execution.execute(request, body);
            long end = System.currentTimeMillis();
            log.info("Response result : {} {}", response.getStatusCode(), response.getStatusText());
            log.info("Request elapsed time : {} ms", end - start);
            return response;
        });
        return restTemplate;
    }

    @Bean
    public SecretConfig secretConfig() throws IOException {
        Path path;
        if (SystemEnvironment.isWindows()) {
            path = Path.of("/config.json");
        } else if (SystemEnvironment.isLinux()) {
            path = Path.of("etc/config.json");
        } else {
            throw new RuntimeException("Unsupported OS");
        }
        String json = Files.readString(path);
        SecretConfig secretConfig = objectMapper.readValue(json, SecretConfig.class);
        return secretConfig;
    }

    @Bean
    public RestClient.Builder restClientBuilder(RestTemplate restTemplate) {
        return RestClient.builder(restTemplate)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

    @Bean
    @Primary
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public RestClient subwayClient(RestClient.Builder builder, SecretConfig secretConfig) {
        RestClient client = builder
                .baseUrl(BASE_URL)
                .defaultHeader("Authorization", AUTHORIZATION_PREFIX + secretConfig.getApiKey().getGovData())
                .build();
        return client;
    }

    @Bean
    @DependsOn("subwayClient")
    public SubwayHttpInterface subwayHttpInterface(@Qualifier("subwayClient") RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(SubwayHttpInterface.class);
    }


}
