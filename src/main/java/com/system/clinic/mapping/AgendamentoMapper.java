package com.system.clinic.mapping;

import com.system.clinic.dto.AgendamentoDTO;
import com.system.clinic.entity.Agendamento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {

    
    AgendamentoDTO toDto(Agendamento agendamento);

    Agendamento toEntity(AgendamentoDTO agendamentoDTO);
}