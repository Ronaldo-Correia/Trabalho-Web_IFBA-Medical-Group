package com.system.clinic.service;

import com.system.clinic.dto.UsuarioDTO;

public interface UsuarioService {
    void save(UsuarioDTO usuario);
    void updatePassword(String email, String newPassword);
    boolean existeUsuarioPorEmail(String email);
}