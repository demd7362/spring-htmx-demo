package com.api.sample.common.security;

import com.api.sample.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public record PrincipalDetails(User user) implements OAuth2User, UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 6036051638701599658L;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }


    @Override
    public String getName() {
        return this.user.getUsername();
    }
}
