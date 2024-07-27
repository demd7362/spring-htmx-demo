package com.api.sample.controller.route;

import com.api.sample.common.util.SecurityUtils;
import com.api.sample.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RouteController {

    @GetMapping("/")
    public String indexRouter() {
        var isAuthenticated = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        log.info("{}" , isAuthenticated);
        return "index";
    }
    @GetMapping("/login")
    public String loginRouter() {
        return "user/login";
    }
    @GetMapping("/join")
    public String joinRouter() {
        return "user/join";
    }
    @GetMapping("/board")
    @Secured("ROLE_USER")
    public String boardRouter() {
        return "board";
    }
    @GetMapping("/generator")
    @Secured("ROLE_USER")
    public String generatorRouter() {
        return "generator";
    }
}
