package com.system.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.system.clinic.entity.Usuario;
import com.system.clinic.dto.UsuarioDTO;
import com.system.clinic.Enum.Role;
import com.system.clinic.repository.UsuarioRepository;
import com.system.clinic.service.UsuarioService;
import com.system.clinic.exception.RecursoDuplicadoException;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final PasswordEncoder passwordEncoder; 
    private final UsuarioRepository repository;

    @Override
    public void save(UsuarioDTO dto) {
        // 1. Verifica se o e-mail já existe (Regra de Negócio)
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RecursoDuplicadoException("Este e-mail já está cadastrado no sistema.");
        }

        // 2. Cria a entidade e mapeia os dados do DTO
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        
        // 3. Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        
        // 4. Define o papel (Admin ou User) vindo do <select> do formulário
        // O value do select deve ser "ROLE_USER" ou "ROLE_ADMIN"
        usuario.setRole(Role.valueOf(dto.getRole()));

        // 5. Persiste no banco
        repository.save(usuario);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        usuario.setSenha(passwordEncoder.encode(newPassword));
        repository.save(usuario);
    }

    @Override
    public boolean existeUsuarioPorEmail(String email) {
        return repository.existsByEmail(email);
    }
}