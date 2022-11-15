package com.company.telegrambotapp.repository.project;

import com.company.telegrambotapp.domains.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 11:12
 * telegram-bot-app/IntelliJ IDEA
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("from OrderItem where order.id = :id")
    Optional<List<OrderItem>> getByOrderId(@Param(value = "id") Long id);

    @Query(value = "from OrderItem where productId = :productId and order.id = :orderId")
    Optional<OrderItem> getProductId(@Param(value = "productId") Long id, @Param(value = "orderId") Long orderId);
}
