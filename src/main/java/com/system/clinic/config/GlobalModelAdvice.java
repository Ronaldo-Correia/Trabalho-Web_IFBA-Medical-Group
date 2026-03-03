package com.system.clinic.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.system.clinic.config.CustomUserDetails;
import com.system.clinic.service.impl.CustomUserDetailsService;

@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute("nome")
    public String nome(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails user) {
            return user.getNome();
        }
        return null;
    }

    // ADICIONE ESTE NOVO MÉTODO:
    @ModelAttribute("isAdmin")
    public boolean isAdmin(Authentication authentication) {
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }
}