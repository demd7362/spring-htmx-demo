package com.api.sample.entity.user;

import com.api.sample.common.enums.LoginType;
import com.api.sample.entity.common.CommonProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends CommonProperties implements Serializable {
    @Serial
    private static final long serialVersionUID = 2292704787934783396L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, columnDefinition = "varchar(50)")
    private String username;
    @Column(nullable = false, columnDefinition = "varchar(200)")
    private String password;
    @Column(name = "login_type")
    @Enumerated(EnumType.STRING)
    private LoginType loginType;


}
