package com.api.sample.common.config;


import com.api.sample.common.constant.Constants;
import com.api.sample.common.security.oauth2.OAuth2UserService;
import com.api.sample.common.security.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                            .loginProcessingUrl("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl(Constants.URL.INDEX);
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/logout")
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
}
