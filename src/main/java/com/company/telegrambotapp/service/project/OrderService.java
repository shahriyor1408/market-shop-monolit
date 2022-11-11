package com.company.telegrambotapp.service.project;

import com.company.telegrambotapp.domains.Order;
import com.company.telegrambotapp.domains.OrderItem;
import com.company.telegrambotapp.exceptions.GenericNotFoundException;
import com.company.telegrambotapp.repository.project.OrderItemRepository;
import com.company.telegrambotapp.repository.project.OrderRepository;
import com.company.telegrambotapp.service.auth.AuthUserService;
import com.company.telegrambotapp.service.bot.MyBot;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 10:52
 * telegram-bot-app/IntelliJ IDEA
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AuthUserService userService;
    private final MyBot myBot;
    private static boolean marked = false;

    @Transactional
    public void order() {
        Order order = getByUserId();
        order.setActive(false);
        myBot.order(order);
        orderRepository.save(order);
    }

    public List<Order> getAll() {
        return orderRepository.getAll().orElseThrow(() -> {
            throw new GenericNotFoundException("Orders is not found!", 404);
        });
    }

    public Order get(@NonNull Long id) {
        return orderRepository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Order is not found!", 404);
        });
    }

    public void mark(@NonNull Long id) {
        Order order = get(id);
        order.setDelivered(true);
        orderRepository.save(order);
    }

    public void addToBasket(@NonNull Long productId) {
        Order order = getByUserId();
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            if (orderItem.getProductId().equals(productId)) {
                orderItem.setCount(orderItem.getCount() + 1);
                marked = true;
            }
        });
        if (!marked) {
            orderItems.add(orderItemRepository.save(OrderItem.childBuilder()
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .count(1)
                    .productId(productId)
                    .createdBy(userService.getCurrentAuthUser().getId())
                    .build()));
        }
        marked = false;
        orderRepository.save(order);
    }

    public Order getByUserId() {
        return orderRepository.getByUserId(userService.getCurrentAuthUser().getId())
                .orElse(orderRepository.save(Order.childBuilder()
                        .userId(userService.getCurrentAuthUser().getId())
                        .orderItems(new ArrayList<>())
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .createdBy(userService.getCurrentAuthUser().getId())
                        .active(true)
                        .build()));
    }

    public Order deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
        return getByUserId();
    }
}
