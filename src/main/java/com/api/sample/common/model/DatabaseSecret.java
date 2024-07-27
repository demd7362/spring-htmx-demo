package com.api.sample.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseSecret {
    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;
}
