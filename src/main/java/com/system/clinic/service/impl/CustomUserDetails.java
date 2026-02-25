package com.system.clinic.service.impl;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String nome;
    private final String email;
    private final String senha;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String nome, String email, String senha,
                             Collection<? extends GrantedAuthority> authorities) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
