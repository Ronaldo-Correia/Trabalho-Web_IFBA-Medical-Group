package com.system.clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // não esqueça de importar!
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "login"; 
    }
}
