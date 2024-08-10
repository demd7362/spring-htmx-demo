package com.api.sample.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecretConfig {
    private Database database;

    @Getter
    @Setter
    public static class Database {
        private String driverClassName;
        private String jdbcUrl;
        private String username;
        private String password;
    }
    private OAuth2 oauth2;

    @Getter
    @Setter
    public static class OAuth2 {
        @JsonProperty("kakao")
        private KakaoClient kakaoClient;

        public static class KakaoClient extends ClientSecret{}
    }
    private ApiKey apiKey;

    @Getter
    @Setter
    public static class ApiKey {
        private String govData;
    }
}
