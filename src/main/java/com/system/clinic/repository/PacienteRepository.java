package com.system.clinic.repository;

import com.system.clinic.entity.PacienteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository  extends JpaRepository<PacienteEntity, Long> {
    List<PacienteEntity> findByNomeContainingIgnoreCase(String nome);
    List<PacienteEntity> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional<PacienteEntity> findByCns(String cns);

}
