package com.system.clinic.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.clinic.dto.AgendamentoDTO;
import com.system.clinic.entity.Agendamento;
import com.system.clinic.entity.PacienteEntity;
import com.system.clinic.entity.ProfissionalEntity;
import com.system.clinic.exception.BusinessException;
import com.system.clinic.exception.DataConsultaPassadaException;
import com.system.clinic.exception.HorarioOcupadoException;
import com.system.clinic.mapping.AgendamentoMapper;
import com.system.clinic.repository.AgendamentoRepository;
import com.system.clinic.repository.PacienteRepository;
import com.system.clinic.repository.ProfissionalRepository;
import com.system.clinic.service.AgendamentoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoMapper agendamentoMapper;
    private final PacienteRepository pacienteRepository;
    private final ProfissionalRepository profissionalRepository;

    @Autowired
    public AgendamentoServiceImpl(
            AgendamentoRepository agendamentoRepository,
            AgendamentoMapper agendamentoMapper,
            PacienteRepository pacienteRepository,
            ProfissionalRepository profissionalRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.agendamentoMapper = agendamentoMapper;
        this.pacienteRepository = pacienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

    @Override
    @Transactional
    public AgendamentoDTO agendarConsulta(AgendamentoDTO agendamentoDTO) {
        // Validação de data/hora no passado
        LocalDate hoje = LocalDate.now();
        LocalTime agora = LocalTime.now();

        if (agendamentoDTO.getDataConsulta().isBefore(hoje)) {
            throw new BusinessException("Não é possível agendar para datas passadas");
        }

        if (agendamentoDTO.getDataConsulta().equals(hoje) &&
                agendamentoDTO.getHoraConsulta().isBefore(agora)) {
            throw new BusinessException("Não é possível agendar para horários passados");
        }

        // Validação de intervalo de 1 hora
        LocalTime horaInicio = agendamentoDTO.getHoraConsulta();
        LocalTime horaFim = horaInicio.plusHours(1);

        boolean conflito = agendamentoRepository.existsByProfissionalIdAndDataConsultaAndHoraConsultaBetween(
                agendamentoDTO.getProfissionalId(),
                agendamentoDTO.getDataConsulta(),
                horaInicio.minusMinutes(59), // Considera 1 minuto antes para arredondamento
                horaFim);

        if (conflito) {
            throw new BusinessException("Intervalo mínimo de 1 hora necessário. Próximo horário disponível: " +
                    horaFim.format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        // Busca entidades relacionadas
        PacienteEntity paciente = pacienteRepository.findById(agendamentoDTO.getPacienteId())
                .orElseThrow(() -> new BusinessException("Paciente não encontrado"));

        ProfissionalEntity profissional = profissionalRepository.findById(agendamentoDTO.getProfissionalId())
                .orElseThrow(() -> new BusinessException("Profissional não encontrado"));

        // Agora preenchemos os nomes diretamente nas entidades
        agendamentoDTO.setNomePaciente(paciente.getNome());
        agendamentoDTO.setNomeProfissional(profissional.getNome());

        // Cria e salva o agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setPacienteId(paciente.getId());
        agendamento.setProfissionalId(profissional.getId());
        agendamento.setCns(agendamentoDTO.getCns());
        agendamento.setEspecialidade(profissional.getEspecialidade());
        agendamento.setDataConsulta(agendamentoDTO.getDataConsulta());
        agendamento.setHoraConsulta(agendamentoDTO.getHoraConsulta());
        agendamento.setMotivoConsulta(agendamentoDTO.getMotivoConsulta());
        agendamento.setNomePaciente(agendamentoDTO.getNomePaciente());
        agendamento.setNomeProfissional(agendamentoDTO.getNomeProfissional());
        agendamento.setStatus("AGENDADO");

        Agendamento salvo = agendamentoRepository.save(agendamento);

        return agendamentoMapper.toDto(salvo);
    }

    @Override
    public List<LocalTime> findHorariosOcupados(Long profissionalId, LocalDate data) {
        return agendamentoRepository.findByProfissionalIdAndDataConsulta(profissionalId, data)
                .stream()
                .map(Agendamento::getHoraConsulta)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> listarAgendamentosCompletos() {
        return agendamentoRepository.findAll().stream()
                .map(agendamento -> {
                    AgendamentoDTO dto = agendamentoMapper.toDto(agendamento);
                    profissionalRepository.findById(agendamento.getProfissionalId())
                            .ifPresent(profissional -> dto.setNomeProfissional(profissional.getNome()));
                    pacienteRepository.findById(agendamento.getPacienteId())
                            .ifPresent(paciente -> dto.setNomePaciente(paciente.getNome())); // Adiciona o nome do
                                                                                             // paciente
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelarAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        agendamento.setStatus("Cancelado");
        agendamentoRepository.save(agendamento);
    }

    @Override
    @Transactional
    public AgendamentoDTO editarAgendamento(Long id, AgendamentoDTO agendamentoDTO) {
        Agendamento agendamentoExistente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

        agendamentoExistente.setDataConsulta(agendamentoDTO.getDataConsulta());
        agendamentoExistente.setHoraConsulta(agendamentoDTO.getHoraConsulta());
        agendamentoExistente.setMotivoConsulta(agendamentoDTO.getMotivoConsulta());
        agendamentoExistente.setStatus(agendamentoDTO.getStatus());

        Agendamento atualizado = agendamentoRepository.save(agendamentoExistente);
        return agendamentoMapper.toDto(atualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoDTO buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .map(agendamentoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Long buscarPacientePorCns(String cns) {
        return pacienteRepository.findByCns(cns)
                .map(PacienteEntity::getId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente com CNS " + cns + " não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Long buscarProfissionalPorId(Long id) {
        return profissionalRepository.findById(id)
                .map(ProfissionalEntity::getId)
                .orElseThrow(() -> new EntityNotFoundException("Profissional com ID " + id + " não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteEntity buscarPacienteEntityPorCns(String cns) {
        return pacienteRepository.findByCns(cns)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com CNS: " + cns));
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalEntity buscarProfissionalEntityPorNome(String nome) {
        return profissionalRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado com nome: " + nome));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> listarPorPaciente(Long pacienteId) {
        return agendamentoRepository.findByPacienteId(pacienteId).stream()
                .map(agendamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendamentoDTO> listarPorProfissional(Long profissionalId) {
        return agendamentoRepository.findByProfissionalId(profissionalId).stream()
                .map(agendamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void voidSave(AgendamentoDTO agendamentoDTO){
        agendamentoRepository.save(agendamentoMapper.toEntity(agendamentoDTO));
    }
    
    public AgendamentoDTO save(AgendamentoDTO agendamentoDTO) {
        // Se houver ID, buscar agendamento existente
        if (agendamentoDTO.getId() != null) {
            Agendamento existingAgendamento = agendamentoRepository.findById(agendamentoDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
            // Atualiza apenas os campos que podem ser modificados
            


            existingAgendamento.setDataConsulta(agendamentoDTO.getDataConsulta());
            existingAgendamento.setHoraConsulta(agendamentoDTO.getHoraConsulta());
            existingAgendamento.setMotivoConsulta(agendamentoDTO.getMotivoConsulta());
            existingAgendamento.setStatus(agendamentoDTO.getStatus());
            // Atualize conforme necessário

            // Salva o agendamento atualizado
            Agendamento updatedEntity = agendamentoRepository.save(existingAgendamento);
            agendamentoRepository.flush();
            return agendamentoMapper.toDto(updatedEntity);
        } else {
            // Caso não tenha ID, cria um novo agendamento
            Agendamento entity = agendamentoMapper.toEntity(agendamentoDTO);
            Agendamento savedEntity = agendamentoRepository.save(entity);
            return agendamentoMapper.toDto(savedEntity);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AgendamentoDTO findOne(Long id) {
        return agendamentoRepository.findById(id)
                .map(agendamentoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com ID: " + id));
    }
}
