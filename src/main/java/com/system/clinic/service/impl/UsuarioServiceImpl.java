package com.system.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.system.clinic.dto.UsuarioDTO;
import com.system.clinic.entity.UsuarioEntity;
import com.system.clinic.exception.RecursoDuplicadoException;
import com.system.clinic.mapping.UsuarioMapper;
import com.system.clinic.repository.PapelRepository;
import com.system.clinic.repository.UsuarioRepository;
import com.system.clinic.service.UsuarioService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioMapper mapper;
    private final UsuarioRepository repository;
    private final PapelRepository papelRepository;
    private final PasswordEncoder passwordEncoder; // Adicionado

    @Override
    public void save(UsuarioDTO usuario) {
        var existeEmail = repository.existsUsuarioEntityByEmail(usuario.getEmail());
        if (existeEmail) {
            throw new RecursoDuplicadoException("Email já cadastrado.");
        }

        var entity = mapper.toEntity(usuario);
        entity.setSenha(passwordEncoder.encode(usuario.getSenha())); // Criptografa a senha

        var papelUser = papelRepository.findByPapel("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Papel ROLE_USER não encontrado"));

        entity.setPapeis(Set.of(papelUser));

        repository.save(entity);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        UsuarioEntity usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        usuario.setSenha(passwordEncoder.encode(newPassword));
        repository.save(usuario);
    }
    @Override
public boolean existeUsuarioPorEmail(String email) {
    return repository.existsUsuarioEntityByEmail(email);
}
}