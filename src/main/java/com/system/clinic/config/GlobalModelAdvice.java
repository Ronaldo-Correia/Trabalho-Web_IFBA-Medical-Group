package com.system.clinic.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.system.clinic.service.impl.CustomUserDetailService;
import com.system.clinic.service.impl.CustomUserDetails;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute("nome")
    public String nome(Authentication authentication) {

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails user) {

            return user.getNome();
        }

        return null;
    }
}
