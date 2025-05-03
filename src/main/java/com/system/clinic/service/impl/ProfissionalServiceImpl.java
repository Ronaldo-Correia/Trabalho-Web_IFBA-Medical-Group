package com.system.clinic.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.clinic.dto.PacienteDTO;
import com.system.clinic.dto.ProfissionalDTO;
import com.system.clinic.entity.PacienteEntity;
import com.system.clinic.entity.ProfissionalEntity;
import com.system.clinic.mapping.ProfissionalMapper;
import com.system.clinic.repository.ProfissionalRepository;
import com.system.clinic.service.ProfissionalService;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class ProfissionalServiceImpl implements ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final ProfissionalMapper profissionalMapper;

    public ProfissionalServiceImpl(ProfissionalRepository profissionalRepository,
            ProfissionalMapper profissionalMapper) {
        this.profissionalRepository = profissionalRepository;
        this.profissionalMapper = profissionalMapper;
    }

    // @Override
    // public ProfissionalDTO findOne(Long id) {
    // return profissionalRepository.findById(id)
    // .map(profissional -> {
    // ProfissionalDTO dto = profissionalMapper.toDto(profissional);
    // dto.setSenha(""); // Limpa a senha para não retornar a senha criptografada
    // return dto;
    // })
    // .orElse(null);
    // }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalDTO findOne(Long id) {
        return profissionalRepository.findById(id)
                .map(profissionalMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado com ID: " + id));
    }

    @Override
    public List<ProfissionalDTO> findAll() {
        return profissionalRepository.findAll()
                .stream()
                .map(profissionalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfissionalDTO> ConsultarPorProfissional(String profissionalNome) {
        return profissionalRepository.findByNomeContainingIgnoreCase(profissionalNome)
                .stream()
                .map(profissionalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProfissionalDTO save(ProfissionalDTO profissionalDTO) {
        ProfissionalEntity entity = profissionalMapper.toEntity(profissionalDTO);
        ProfissionalEntity savedEntity = profissionalRepository.save(entity);
        return profissionalMapper.toDto(savedEntity);
    }

    @Override
    public void remove(Long id) {
        if (!profissionalRepository.existsById(id)) {
            throw new EntityNotFoundException("Paciente não encontrado com ID: " + id);
        }
        profissionalRepository.deleteById(id);
    }

    @Override
    public List<ProfissionalDTO> findByCpf(String cpf) {
        return profissionalRepository.findByCpf(cpf)
                .stream()
                .map(profissionalMapper::toDto)
                .collect(Collectors.toList());
    }

    // @Override
    // public ProfissionalDTO save(ProfissionalDTO profissionalDTO) {
    // ProfissionalEntity profissional =
    // profissionalMapper.toEntity(profissionalDTO);
    // profissional.setSenha(passwordEncoder.encode(profissionalDTO.getSenha())); //
    // Criptografa a senha
    // ProfissionalEntity savedEntity = profissionalRepository.save(profissional);
    // return profissionalMapper.toDto(savedEntity);
    // }

    // @Override
    // public void salvarProfissional(ProfissionalDTO profissionalDTO) {
    // ProfissionalEntity profissional =
    // profissionalMapper.toEntity(profissionalDTO);
    // profissional.setSenha(passwordEncoder.encode(profissionalDTO.getSenha())); //
    // Criptografa a senha
    // profissionalRepository.save(profissional);
    // }

    // @Override
    // public void update(Long id, ProfissionalDTO dto, String novaSenha) {
    // ProfissionalEntity profissional =
    // profissionalRepository.findById(id).orElse(null);
    // if (profissional != null) {
    // Atualiza os campos do profissional
    // profissional.setNome(dto.getNome());
    // profissional.setEmail(dto.getEmail());
    // profissional.setCpf(dto.getCpf());
    // profissional.setCns(dto.getCns());
    // profissional.setTelefone(dto.getTelefone());
    // profissional.setDataNascimento(dto.getDataNascimento());
    // profissional.setEspecialidade(dto.getEspecialidade());
    // profissional.setBairro(dto.getBairro());

    // Se nova senha foi fornecida, criptografa e atualiza
    // if (novaSenha != null && !novaSenha.isEmpty()) {
    // profissional.setSenha(passwordEncoder.encode(novaSenha));
    // }

    // profissionalRepository.save(profissional);
    // }
    // }

}
