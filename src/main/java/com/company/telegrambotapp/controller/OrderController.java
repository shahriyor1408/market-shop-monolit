package com.company.telegrambotapp.controller;

import com.company.telegrambotapp.domains.Order;
import com.company.telegrambotapp.dtos.basket.OrderCreateDto;
import com.company.telegrambotapp.response.ApiResponse;
import com.company.telegrambotapp.service.project.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 08/11/22 Tuesday 10:52
 * telegram-bot-app/IntelliJ IDEA
 */
@RestController
public class OrderController extends ApiController<OrderService> {

    protected OrderController(OrderService service) {
        super(service);
    }

    @PostMapping(PATH + "/order/book")
    public ApiResponse<Void> order() {
        service.order();
        return new ApiResponse<>(200, true);
    }

    @GetMapping(PATH + "/order/getAll")
    public ApiResponse<List<Order>> getAll() {
        return new ApiResponse<>(service.getAll(), 200);
    }

    @GetMapping(PATH + "/order/get/{id}")
    public ApiResponse<Order> get(@PathVariable Long id) {
        return new ApiResponse<>(service.get(id));
    }

    @GetMapping(PATH + "/order/basket")
    public ApiResponse<Order> basket() {
        return new ApiResponse<>(service.getByUserId());
    }

    @PostMapping(PATH + "/order/addToBasket/{productId}")
    public ApiResponse<Void> addToBasket(@PathVariable Long productId) {
        service.addToBasket(productId);
        return new ApiResponse<>(200, true);
    }


    @PostMapping(PATH + "/order/mark/{id}")
    public ApiResponse<Void> mark(@PathVariable Long id) {
        service.mark(id);
        return new ApiResponse<>(200, true);
    }

    @DeleteMapping(PATH + "/order/delete/{orderItemId}")
    public ApiResponse<Order> delete(@PathVariable Long orderItemId) {
        return new ApiResponse<>(service.deleteOrderItem(orderItemId));
    }
}
