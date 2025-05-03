package com.system.clinic.config;

import com.system.clinic.entity.PapelEntity;
import com.system.clinic.entity.UsuarioEntity;
import com.system.clinic.repository.PapelRepository;
import com.system.clinic.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Criar papel ADMIN se não existir
            PapelEntity papelAdmin = papelRepository.findByPapel("ROLE_ADMIN")
                    .orElseGet(() -> {
                        PapelEntity p = new PapelEntity();
                        p.setPapel("ROLE_ADMIN");
                        return papelRepository.save(p);
                    });
            PapelEntity papelUser = papelRepository.findByPapel("ROLE_USER")
                    .orElseGet(() -> papelRepository.save(new PapelEntity(null, "ROLE_USER")));

            // Criar usuário admin se não existir
            if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
                UsuarioEntity admin = new UsuarioEntity();
                admin.setNome("Administrador");
                admin.setEmail("admin@admin.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setPapeis(Set.of(papelAdmin));

                usuarioRepository.save(admin);
                System.out.println("Usuário admin criado: admin@admin.com / admin123");
            }
        };
    }
}
