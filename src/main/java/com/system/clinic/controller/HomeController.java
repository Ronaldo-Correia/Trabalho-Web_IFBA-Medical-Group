package com.system.clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

// IMPORTAÇÕES FALTANTES
import com.system.clinic.entity.Usuario;
import com.system.clinic.repository.UsuarioRepository;
import com.system.clinic.Enum.Role;

@Controller
public class HomeController {

    // Adicione o repositório para poder buscar o usuário
    private final UsuarioRepository usuarioRepository;

    public HomeController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            
            // Busca o usuário no banco pelo email vindo do Security
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
            
            if (usuario != null) {
                model.addAttribute("nome", usuario.getNome());
                // Passa o booleano isAdmin para o JTE
                model.addAttribute("isAdmin", usuario.getRole() == Role.ROLE_ADMIN);
            }
        } else {
            // Caso não esteja logado, garante que isAdmin seja false
            model.addAttribute("isAdmin", false);
        }
        return "home";
    }
}