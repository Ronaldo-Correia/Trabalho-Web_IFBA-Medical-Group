package com.system.clinic.config;

import com.system.clinic.entity.Usuario;
import com.system.clinic.Enum.Role;
import com.system.clinic.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitAdmin implements CommandLineRunner {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public InitAdmin(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
public void run(String... args) throws Exception {
    if (repository.findByEmail("admin@admin.com").isEmpty()) {
        Usuario admin = new Usuario();
        admin.setNome("Administrador");
        admin.setEmail("admin@admin.com");
        admin.setSenha(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ROLE_ADMIN);
        repository.save(admin);
    }
} 
} 