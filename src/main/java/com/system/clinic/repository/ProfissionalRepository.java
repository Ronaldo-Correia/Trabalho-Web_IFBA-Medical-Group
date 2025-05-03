package com.system.clinic.repository;

import com.system.clinic.entity.Agendamento;
import com.system.clinic.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity, Long> {
    Optional<ProfissionalEntity> findById(Long id);
    Optional<ProfissionalEntity> findByNome(String nome);
    Optional<ProfissionalEntity> findByNomeContainingIgnoreCase(String nome);

    List<ProfissionalEntity> findByEspecialidadeContainingIgnoreCase(String especialidade);
    List<ProfissionalEntity> findByCpf(String cpf);

}
