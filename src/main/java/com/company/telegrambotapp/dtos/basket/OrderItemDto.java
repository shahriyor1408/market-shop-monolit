package com.company.telegrambotapp.dtos.basket;

import lombok.*;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 11/11/22 Friday 11:17
 * telegram-bot-app/IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;
    private Long productId;
    private Integer count;
}
