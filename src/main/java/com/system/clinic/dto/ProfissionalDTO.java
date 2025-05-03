package com.system.clinic.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfissionalDTO {

    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    private LocalDate dataNascimento;

    @NotBlank
    private String especialidade;

    @NotBlank
    private String telefone;

 

    // @NotBlank
    // private String senha;

    @NotBlank
    private String cns;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Formato de CPF inválido")
    private String cpf;

    @NotNull
    private String email;


    public String getDataNascimentoFormatada() {
        if (dataNascimento != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return dataNascimento.format(formatter);
        }
        return null;
    }
    @NotBlank(message = "Município é obrigatório")
    private String municipio;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Formato inválido. Ex: 12345-678 ou 12345678")
    private String cep;

}
