package com.api.sample.controller.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

    @GetMapping("/")
    public String indexRouter() {
        return "index";
    }
    @GetMapping("/login")
    public String loginRouter() {
        return "login";
    }
    @GetMapping("/join")
    public String joinRouter() {
        return "join";
    }
    @GetMapping("/board")
    public String boardRouter() {
        return "board";
    }
}
