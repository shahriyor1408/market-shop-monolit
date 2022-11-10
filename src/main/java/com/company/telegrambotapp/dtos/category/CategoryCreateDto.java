package com.company.telegrambotapp.dtos.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/11/22 Monday 10:00
 * telegram-bot-app/IntelliJ IDEA
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto {

    @NotBlank(message = "Name can not be null!")
    @NotNull(message = "Name can not be null!")
    private String name;
}
