package com.company.telegrambotapp.dtos.basket;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @NotNull(message = "Order items can not be null!")
    private List<OrderItemCreateDto> orderItems;

    @NotNull(message = "Location can not be null!")
    @NotBlank(message = "Location can not be blank!")
    private String location;

    @NotNull(message = "Total price can not be null!")
    private Integer totalPrice;
}
