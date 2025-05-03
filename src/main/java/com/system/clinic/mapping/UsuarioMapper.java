package com.system.clinic.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.system.clinic.dto.UsuarioDTO;
import com.system.clinic.entity.UsuarioEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    UsuarioEntity toEntity(UsuarioDTO dto);

}
