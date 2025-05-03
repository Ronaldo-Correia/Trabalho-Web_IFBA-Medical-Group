package com.system.clinic.mapping;

import com.system.clinic.entity.ProfissionalEntity;
import com.system.clinic.dto.ProfissionalDTO;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProfissionalMapper {

    public abstract ProfissionalEntity toEntity(ProfissionalDTO dto);

    public abstract ProfissionalDTO toDto(ProfissionalEntity entity);
}
