package com.company.telegrambotapp.service.project;

import com.company.telegrambotapp.domains.Order;
import com.company.telegrambotapp.domains.OrderItem;
import com.company.telegrambotapp.dtos.basket.OrderCreateDto;
import com.company.telegrambotapp.dtos.basket.OrderDto;
import com.company.telegrambotapp.dtos.basket.OrderItemDto;
import com.company.telegrambotapp.exceptions.GenericNotFoundException;
import com.company.telegrambotapp.mapper.OrderMapper;
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
import java.util.Optional;

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
    private final ProductService productService;
    private final MyBot myBot;
    private final OrderMapper mapper;

    @Transactional
    public void order(@NonNull OrderCreateDto dto) {
        Order order = orderRepository.getByUserId(userService.getCurrentAuthUser().getId()).orElseThrow(() -> {
            throw new GenericNotFoundException("Order is not found!", 404);
        });
        order.setActive(true);
        order.setLocation(dto.getLocation());
        orderRepository.save(order);
        List<OrderItem> orderItems = orderItemRepository.getByOrderId(order.getId()).orElseThrow(() -> {
            throw new GenericNotFoundException("OrderItem not found!", 404);
        });
        myBot.order(order, orderItems);
    }

    @Transactional
    public List<OrderDto> getAll() {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Order> orders = orderRepository.getAll().orElseThrow(() -> {
            throw new GenericNotFoundException("Orders is not found!", 404);
        });
        orders.forEach(order -> orderDtos.add(get(order.getId())));
        return orderDtos;
    }

    @Transactional
    public OrderDto get(@NonNull Long id) {
        Order order = orderRepository.get(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Order is not found!", 404);
        });
        OrderDto dto = mapper.toDto(order);
        List<OrderItem> orderItems = orderItemRepository.getByOrderId(order.getId()).orElseThrow(() -> {
            throw new GenericNotFoundException("OrderItems is not found!", 404);
        });

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            orderItemDtoList.add(OrderItemDto.builder()
                    .id(orderItem.getId())
                    .product(productService.get(orderItem.getProductId()))
                    .count(orderItem.getCount())
                    .build());
        }
        dto.setOrderItems(orderItemDtoList);
        return dto;
    }

    @Transactional
    public void mark(@NonNull Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            throw new GenericNotFoundException("Order not found!", 404);
        });
        order.setDelivered(true);
        orderRepository.save(order);
    }

    @Transactional
    public void addToBasket(@NonNull Long productId) {
        OrderDto basket = basket();
        Order order = orderRepository.get(basket.getId()).orElseThrow(() -> {
            throw new GenericNotFoundException("Order not found!", 404);
        });

        Optional<OrderItem> itemOptional = orderItemRepository.getProductId(productId, order.getId());
        if (itemOptional.isEmpty()) {
            orderItemRepository.save(OrderItem.childBuilder()
                    .productId(productId)
                    .count(1)
                    .order(order)
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .createdBy(userService.getCurrentAuthUser().getId())
                    .build());
        } else {
            OrderItem orderItem = itemOptional.get();
            orderItem.setCount(orderItem.getCount() + 1);
            orderItemRepository.save(orderItem);
        }

        double totalPrice = 0;
        List<OrderItem> orderItems = orderItemRepository.getByOrderId(order.getId()).orElse(new ArrayList<>());
        for (OrderItem orderItem : orderItems) {
            totalPrice += productService.get(orderItem.getProductId()).getPrice() * orderItem.getCount();
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
    }

    @Transactional
    public OrderDto basket() {
        Optional<Order> optionalOrder = orderRepository.getByUserId(userService.getCurrentAuthUser().getId());
        if (optionalOrder.isEmpty()) {
            Order order = Order.childBuilder()
                    .userId(userService.getCurrentAuthUser().getId())
                    .createdBy(userService.getCurrentAuthUser().getId())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .totalPrice(0D)
                    .build();
            return mapper.toDto(orderRepository.save(order));
        }
        return this.get(optionalOrder.get().getId());
    }

    public OrderDto deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
        return basket();
    }
}
