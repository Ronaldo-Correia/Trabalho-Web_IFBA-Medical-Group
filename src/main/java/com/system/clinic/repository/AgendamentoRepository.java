package com.system.clinic.repository;

import com.system.clinic.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    // Método para verificar conflito de horário
    public boolean existsByProfissionalIdAndDataConsultaAndHoraConsulta(Long profissionalId, LocalDate dataConsulta, LocalTime horaConsulta);
    boolean existsByDataConsultaAndHoraConsulta(LocalDate dataConsulta, LocalTime horaConsulta);

    List<Agendamento> findByPacienteId(Long pacienteId);
    List<Agendamento> findByProfissionalId(Long profissionalId);
     @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM Agendamento a " +
           "WHERE a.profissionalId = :profissionalId " +
           "AND a.dataConsulta = :dataConsulta " +
           "AND a.horaConsulta BETWEEN :horaInicio AND :horaFim")
    boolean existsByProfissionalIdAndDataConsultaAndHoraConsultaBetween(
        @Param("profissionalId") Long profissionalId,
        @Param("dataConsulta") LocalDate dataConsulta,
        @Param("horaInicio") LocalTime horaInicio,
        @Param("horaFim") LocalTime horaFim);
        List<Agendamento> findByProfissionalIdAndDataConsulta(Long profissionalId, LocalDate dataConsulta);
}