package com.system.clinic.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController; // IMPORTANTE: Adicione este import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController { // IMPLEMENTE AQUI

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Verifica se o parâmetro "forbidden" veio na URL (vinda do SecurityConfig)
        String forbidden = request.getParameter("forbidden");
        if ("true".equals(forbidden)) {
            model.addAttribute("forbidden", true);
        }
        
        // Captura o código de status real (404, 500, etc)
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            if (statusCode == 500) {
                model.addAttribute("serverError", true);
            } else if (statusCode == 404) {
                model.addAttribute("notFound", true);
            }
        }

        return "error"; // Renderiza o seu error.jte
    }
}