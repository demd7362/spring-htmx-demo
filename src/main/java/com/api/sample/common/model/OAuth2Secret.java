package com.api.sample.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2Secret {
    @JsonProperty("kakao")
    private KakaoClient kakaoClient;

    public static class KakaoClient extends ClientSecret{}
}
