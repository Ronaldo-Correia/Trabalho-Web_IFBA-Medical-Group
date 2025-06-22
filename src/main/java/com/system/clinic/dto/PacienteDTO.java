package com.system.clinic.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PacienteDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    private String dataNascimentoFormatada; // <--- Adicionado!

    @NotBlank(message = "Sexo é obrigatório")
    private String sexo;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido")
    private String cpf;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

      public String getDataNascimentoFormatada() {
        if (dataNascimento != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return dataNascimento.format(formatter);
        }
        return null;
    }

    @NotBlank(message = "CNS é obrigatório")
    private String cns;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Município é obrigatório")
    private String municipio;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Formato inválido. Ex: 12345-678 ou 12345678")
    private String cep;
}
