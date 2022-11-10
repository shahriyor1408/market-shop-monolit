package com.company.telegrambotapp.mapper;

import com.company.telegrambotapp.domains.auth.AuthRole;
import com.company.telegrambotapp.dtos.auth.AuthRoleCreateDTO;
import com.company.telegrambotapp.dtos.auth.AuthRoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthRoleMapper {
    AuthRoleDTO toDTO(AuthRole entity);

    List<AuthRoleDTO> toDTO(List<AuthRole> entities);

    AuthRole fromCreateDTO(AuthRoleCreateDTO dto);
}
