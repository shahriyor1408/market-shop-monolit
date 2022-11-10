package com.company.telegrambotapp.mapper;

import com.company.telegrambotapp.domains.auth.AuthUser;
import com.company.telegrambotapp.dtos.UserRegisterDTO;
import com.company.telegrambotapp.dtos.auth.AuthUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUser fromRegisterDTO(UserRegisterDTO dto);

    AuthUserDTO toDTO(AuthUser domain);
}
