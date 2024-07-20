package com.api.sample.controller.route;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
