package com.system.clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.system.clinic.dto.PacienteDTO;

@Service
public interface PacienteService {

    PacienteDTO findOne(Long id);

    List<PacienteDTO> findAll();

    List<PacienteDTO> ConsultarPorPaciente(String name);

    PacienteDTO save(PacienteDTO pacienteDTO);

    void remove(Long id);

    List<PacienteDTO> findByCpf(String cpf);
}