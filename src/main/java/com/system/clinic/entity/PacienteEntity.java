package com.system.clinic.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paciente")
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @Past(message = "Data de nascimento deve ser no passado")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "Sexo é obrigatório")
    @Column(nullable = false, length = 10) // Masculino, Feminino, Outro
    private String sexo;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Email(message = "E-mail inválido")
    @Column(nullable = false, length = 100)
    private String email;

    @NotBlank(message = "CNS é obrigatório")
    @Column(unique = true, length = 15)
    private String cns;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(length = 15)
    private String telefone;

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
