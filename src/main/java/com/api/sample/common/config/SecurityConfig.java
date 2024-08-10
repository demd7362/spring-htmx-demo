package com.api.sample.common.config;


import com.api.sample.common.constant.Constants;
import com.api.sample.model.SecretConfig;
import com.api.sample.common.security.CustomAccessDeniedHandler;
import com.api.sample.common.security.oauth2.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.fromHierarchy("""
                ROLE_ADMIN > ROLE_USER
                """);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage(Constants.URL.LOGIN)
                            .loginProcessingUrl(Constants.URL.LOGIN)
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl(Constants.URL.INDEX);
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl(Constants.URL.LOGOUT)
                            .deleteCookies("JSESSIONID")
                            .logoutSuccessUrl(Constants.URL.INDEX)
                            .permitAll();
                })
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer
                            .defaultSuccessUrl(Constants.URL.INDEX)
                            .loginPage(Constants.URL.LOGIN)
                            .userInfoEndpoint(userInfoEndpointConfig -> {
                                userInfoEndpointConfig.userService(oAuth2UserService);
                            });

                })
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
                    httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new CustomAccessDeniedHandler());
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer
                            .sessionFixation()
                            .newSession()
                            .maximumSessions(1);
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
//                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                            .requestMatchers("/","/login","/logout","/join").permitAll()
                            .anyRequest().permitAll();
                })
                .build();
    }
    @Bean
    @DependsOn("secretConfig")
    public ClientRegistrationRepository clientRegistrationRepository(SecretConfig secretConfig) {
        return new InMemoryClientRegistrationRepository(this.kakaoClientRegistration(secretConfig.getOauth2()));
    }

    private ClientRegistration kakaoClientRegistration(SecretConfig.OAuth2 oAuth2) {
        return ClientRegistration.withRegistrationId("kakao") // /oauth2/authorization/{withRegistrationId} url로 접근해서 인증 시작
                .clientId(oAuth2.getKakaoClient().getClientId())
                .clientSecret(oAuth2.getKakaoClient().getClientSecret())
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .redirectUri("http://localhost/login/oauth2/code/kakao")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname")
                .build();
    }
}
