package com.system.clinic.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {
    private Long id;

    @NotNull(message = "O paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "O profissional é obrigatório")
    private Long profissionalId;

    @NotBlank
    private String especialidade;

    @NotNull
    private LocalDate dataConsulta;

    @NotNull
    private LocalTime horaConsulta;

    @NotBlank
    private String motivoConsulta;

    @NotBlank
    private String status;

    @NotBlank(message = "O CNS é obrigatório")

    private String cns;
    
    // Campos adicionais para a tela
    private String nomePaciente;
    private String nomeProfissional;

    public String getDataConsultaFormatada() {
        return dataConsulta != null ? dataConsulta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    public String getHoraConsultaFormatada() {
        return horaConsulta != null ? horaConsulta.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }

    public String getStatusTexto() {
        return switch (status.toLowerCase()) {
            case "agendado" -> "Agendado";
            case "concluido" -> "Concluído";
            case "cancelado" -> "Cancelado";
            default -> "Desconhecido";
        };
    }

}