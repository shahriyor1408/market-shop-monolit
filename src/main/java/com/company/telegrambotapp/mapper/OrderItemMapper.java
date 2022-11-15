package com.company.telegrambotapp.mapper;

import com.company.telegrambotapp.domains.OrderItem;
import com.company.telegrambotapp.dtos.basket.OrderItemDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 12/11/22 Saturday 09:33
 * telegram-bot-app/IntelliJ IDEA
 */
@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    List<OrderItemDto> toDto(List<OrderItem> orderItems);
}
