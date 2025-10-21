package com.system.clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Home pública
    @GetMapping("/")
    public String index() {
        return "index"; // página pública
    }

    // Home interna (pós-login)
    @GetMapping("/home")
    public String home() {
        return "home"; // página logada
    }
}
