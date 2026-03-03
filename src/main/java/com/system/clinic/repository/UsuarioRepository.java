package com.system.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system.clinic.entity.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> { // Use Usuario aqui
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}