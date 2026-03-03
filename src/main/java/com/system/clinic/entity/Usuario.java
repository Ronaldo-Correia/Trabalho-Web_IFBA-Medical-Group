package com.system.clinic.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.system.clinic.Enum.Role;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O segredo está aqui!
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;
}