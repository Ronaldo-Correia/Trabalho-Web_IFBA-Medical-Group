package com.system.clinic.mapping;

import com.system.clinic.entity.PacienteEntity;
import com.system.clinic.dto.PacienteDTO;
import org.mapstruct.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Mapper(componentModel = "spring")
public abstract class PacienteMapper {

    public abstract PacienteEntity toEntity(PacienteDTO dto);

    public abstract PacienteDTO toDto(PacienteEntity entity);

}
