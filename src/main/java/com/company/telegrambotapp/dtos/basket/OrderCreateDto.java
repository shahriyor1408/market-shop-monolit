package com.company.telegrambotapp.dtos.basket;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 10:56
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDto {

    @NotNull(message = "Location can not be null!")
    @NotBlank(message = "Location can not be blank!")
    private String location;
}
