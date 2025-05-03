package com.system.clinic.service.impl;

import com.system.clinic.dto.PacienteDTO;
import com.system.clinic.entity.PacienteEntity;
import com.system.clinic.mapping.PacienteMapper;
import com.system.clinic.repository.PacienteRepository;
import com.system.clinic.service.PacienteService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    public PacienteServiceImpl(PacienteRepository pacienteRepository,
                             PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteDTO findOne(Long id) {
        return pacienteRepository.findById(id)
                .map(pacienteMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> ConsultarPorPaciente(String pacienteNome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(pacienteNome)
                .stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PacienteDTO save(PacienteDTO pacienteDTO) {
        PacienteEntity entity = pacienteMapper.toEntity(pacienteDTO);
        PacienteEntity savedEntity = pacienteRepository.save(entity);
        return pacienteMapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paciente não encontrado com ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteDTO> findByCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf)
                .stream()
                .map(pacienteMapper::toDto)
                .collect(Collectors.toList());
    }
}