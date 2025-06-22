package com.system.clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.system.clinic.consts.RequestPathConstants.LOGIN;

@Controller
@RequestMapping(LOGIN)
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

}
