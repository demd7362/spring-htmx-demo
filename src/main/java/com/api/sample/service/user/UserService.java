package com.api.sample.service.user;

import com.api.sample.common.enums.Role;
import com.api.sample.common.exception.HtmxException;
import com.api.sample.common.tool.Html;
import com.api.sample.dto.user.JoinRequestDto;
import com.api.sample.entity.user.User;
import com.api.sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(JoinRequestDto joinRequestDto){
        String username = joinRequestDto.getUsername();
        String password = joinRequestDto.getPassword();
        if(userRepository.existsByUsername(username)){
            throw new HtmxException(Html.openDialog("회원가입", "아이디가 중복됩니다."));
        }
        if(!password.equals(joinRequestDto.getPasswordConfirmation())){
            throw new HtmxException(Html.openDialog("회원가입", "비밀번호가 일치하지 않습니다."));
        }
        String encryptedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .username(username)
                .password(encryptedPassword)
                .role(Role.ROLE_USER)
                .build();
        return userRepository.save(user);
    }
}
