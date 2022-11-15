package com.company.telegrambotapp.mapper;

import com.company.telegrambotapp.domains.Order;
import com.company.telegrambotapp.dtos.basket.OrderDto;
import org.mapstruct.Mapper;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 12/11/22 Saturday 09:12
 * telegram-bot-app/IntelliJ IDEA
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
