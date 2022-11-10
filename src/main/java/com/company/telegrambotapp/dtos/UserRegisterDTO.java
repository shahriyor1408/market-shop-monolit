package com.company.telegrambotapp.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {
    @NotBlank(message = "Username can not be blank")
    @NotNull(message = "Username can not be null!")
    private String username;

    @NotBlank(message = "Password can not be blank")
    @NotNull(message = "Password can not be null!")
    private String password;

    @NotBlank(message = "Telephone can not be blank!")
    @NotNull(message = "Telephone can not be null!")
    private String telephone;
}
