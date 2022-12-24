package com.company.telegrambotapp.dtos.basket;

import com.company.telegrambotapp.dtos.product.ProductDto;
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
    private ProductDto product;
    private Integer count;
}
