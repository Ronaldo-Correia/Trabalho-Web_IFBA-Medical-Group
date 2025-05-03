// ProfissionalService.java (interface)
package com.system.clinic.service;

import com.system.clinic.dto.ProfissionalDTO;
import java.util.List;

public interface ProfissionalService {

    ProfissionalDTO findOne(Long id);

    List<ProfissionalDTO> findAll();

    List<ProfissionalDTO> ConsultarPorProfissional(String name);

    ProfissionalDTO save(ProfissionalDTO profissionalDTO);

    void remove(Long id);

    List<ProfissionalDTO> findByCpf(String cpf);

}