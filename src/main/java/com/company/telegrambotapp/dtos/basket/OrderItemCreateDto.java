package com.company.telegrambotapp.dtos.basket;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 11:08
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemCreateDto {

    @NotNull(message = "Product id can not be null!")
    private Long productId;

    @NotNull(message = "Count can not b null!")
    private Integer count;
}
