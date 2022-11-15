package com.company.telegrambotapp.dtos.basket;

import lombok.*;

import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 11/11/22 Friday 11:06
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
    private Long userId;
    private double totalPrice;
    private String location;
    private boolean delivered;
}
