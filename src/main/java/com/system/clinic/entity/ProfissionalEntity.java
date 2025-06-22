package com.system.clinic.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profissional")
public class ProfissionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Especialidade é obrigatória")
    @Column(name = "especialidade", nullable = false)
    private String especialidade;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(length = 15)
    private String telefone;

    @Column(name = "cns", length = 15)
    private String cns;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Município é obrigatório")
    @Column(length = 100)
    private String municipio;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(length = 100)
    private String bairro;

    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Formato inválido. Use 12345-678 ou 12345678")
    @Column(length = 9) 
    private String cep;
}
