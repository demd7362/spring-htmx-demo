package com.api.sample.common.oauth2;

import com.api.sample.common.enums.LoginType;
import com.api.sample.common.exception.HtmxException;
import com.api.sample.common.security.PrincipalDetails;
import com.api.sample.common.tool.Html;
import com.api.sample.entity.user.User;
import com.api.sample.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public OAuth2UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    // code로 액세스토큰 발급 완료시점
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, String> properties = oAuth2User.getAttribute("properties");
        String username = properties.get("nickname");
        Long password = oAuth2User.getAttribute(userNameAttributeName); // provider마다 다르므로 "id" 값 고정이여서 안됨
        Assert.notNull(username, "cannot find username property");
        Assert.notNull(password, "cannot find id property");
        String encryptedPassword = passwordEncoder.encode(password.toString());
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            // 2. resistrationId 가져오기 (third-party id)
            String registrationId = userRequest.getClientRegistration().getRegistrationId();


            LoginType loginType = LoginType.from(registrationId);
            User newUser = User.builder()
                    .username(username)
                    .password(encryptedPassword)
                    .loginType(loginType)
                    .build();
            return userRepository.save(newUser);
        });
        return new PrincipalDetails(user);
    }
}
