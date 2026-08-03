package com.kubeflux.orderservice.service;

import com.kubeflux.orderservice.event.OrderCreatedEvent;
import com.kubeflux.orderservice.kafka.OrderEventProducer;
import com.kubeflux.orderservice.model.Order;
import com.kubeflux.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer eventProducer;

    public OrderService(OrderRepository orderRepository, OrderEventProducer eventProducer) {
        this.orderRepository = orderRepository;
        this.eventProducer = eventProducer;
    }

    public Order createOrder(String productId, Integer quantity, String customerId) {
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setCustomerId(customerId);
        order.setStatus(Order.OrderStatus.CREATED);
        order.setCreatedAt(Instant.now());

        Order saved = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                saved.getOrderId(), productId, quantity, customerId
        );
        eventProducer.publishOrderCreated(event);

        return saved;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}