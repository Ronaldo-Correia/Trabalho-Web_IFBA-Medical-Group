package com.system.clinic.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    @NotNull(message = "Não pode ser vazio")
    @Size(min = 6, max = 50)
    private String senha;

    @NotNull(message = "Não pode ser vazio")
    @Email
    private String email;

    @NotNull(message = "Não pode ser vazio")
    private String nome;

}
