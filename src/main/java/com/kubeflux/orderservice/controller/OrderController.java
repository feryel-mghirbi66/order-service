package com.kubeflux.orderservice.controller;

import com.kubeflux.orderservice.model.Order;
import com.kubeflux.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Map<String, Object> request) {
        String productId = (String) request.get("productId");
        Integer quantity = (Integer) request.get("quantity");
        String customerId = (String) request.get("customerId");
        return orderService.createOrder(productId, quantity, customerId);
    }
}