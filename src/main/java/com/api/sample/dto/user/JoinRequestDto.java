package com.api.sample.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDto {
    private String username;
    private String password;
    private String passwordConfirmation;
}
