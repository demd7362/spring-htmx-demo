package com.api.sample.controller.html;

import com.api.sample.common.annotation.HtmxRenderer;
import com.api.sample.common.tool.Html;
import com.api.sample.dto.user.JoinRequestDto;
import com.api.sample.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@HtmxRenderer
@RequiredArgsConstructor
public class UserRenderer {
    private final UserService userService;

    @PostMapping("/join/script")
    public ResponseEntity<String> joinRenderer(JoinRequestDto joinRequestDto){
        userService.join(joinRequestDto);
        Html root = Html.javaScript("navigate('/login?username=%s')".formatted(joinRequestDto.getUsername()));
        return ResponseEntity.ok(root.toString());
    }
}
