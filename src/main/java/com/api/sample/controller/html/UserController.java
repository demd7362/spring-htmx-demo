package com.api.sample.controller.html;

import com.api.sample.common.annotation.HtmxController;
import com.api.sample.common.tool.Html;
import com.api.sample.dto.user.JoinRequestDto;
import com.api.sample.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

@HtmxController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public String join(JoinRequestDto joinRequestDto){
        userService.join(joinRequestDto);
        Html root = Html.javaScript("navigate('/login?username=%s')".formatted(joinRequestDto.getUsername()));
        return root.toString();
    }
}
