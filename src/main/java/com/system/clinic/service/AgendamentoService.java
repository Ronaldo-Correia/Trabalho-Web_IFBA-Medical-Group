package com.system.clinic.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.system.clinic.dto.AgendamentoDTO;
import com.system.clinic.entity.PacienteEntity;
import com.system.clinic.entity.ProfissionalEntity;

public interface AgendamentoService {

    AgendamentoDTO agendarConsulta(AgendamentoDTO agendamentoDTO);

    List<AgendamentoDTO> listarAgendamentosCompletos();

    void cancelarAgendamento(Long id);

    AgendamentoDTO editarAgendamento(Long id, AgendamentoDTO agendamentoDTO);

    AgendamentoDTO buscarPorId(Long id);

    Long buscarPacientePorCns(String cns);

    Long buscarProfissionalPorId(Long id);

    PacienteEntity buscarPacienteEntityPorCns(String cns);

    ProfissionalEntity buscarProfissionalEntityPorNome(String nome);

    List<LocalTime> findHorariosOcupados(Long profissionalId, LocalDate data);

    List<AgendamentoDTO> listarPorPaciente(Long pacienteId);

    List<AgendamentoDTO> listarPorProfissional(Long profissionalId);

    AgendamentoDTO findOne(Long id);

    AgendamentoDTO save(AgendamentoDTO agendamentoDTO);

    void voidSave(AgendamentoDTO agendamentoDTO);
}