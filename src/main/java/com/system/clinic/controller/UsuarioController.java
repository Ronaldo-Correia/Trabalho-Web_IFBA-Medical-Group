package com.system.clinic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.clinic.entity.Usuario;
import com.system.clinic.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository repository;

    public UsuarioController(PasswordEncoder passwordEncoder, UsuarioRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model, Authentication auth) {

        if (!auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/acesso-negado";
        }

        model.addAttribute("usuario", new Usuario());
        return "usuarios/cadastroUsuario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario,
                        Authentication auth,
                        Model model) {

        if (!auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/acesso-negado";
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("erro", "Este e-mail já está cadastrado.");
            return "usuarios/cadastroUsuario";
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        repository.save(usuario);

        return "usuarios/cadastroSucesso";
    }

}
