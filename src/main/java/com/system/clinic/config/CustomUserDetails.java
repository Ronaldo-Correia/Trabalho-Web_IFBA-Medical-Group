package com.system.clinic.config;

import com.system.clinic.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    // Removido o UsuarioRepository daqui, não é necessário nesta classe
    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return usuario.getNome();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Pega o nome do Enum (ex: ROLE_ADMIN ou ROLE_USER)
        return List.of(new SimpleGrantedAuthority(usuario.getRole().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}