package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 11:12
 * telegram-bot-app/IntelliJ IDEA
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
