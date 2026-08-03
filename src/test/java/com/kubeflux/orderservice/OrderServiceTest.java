package com.kubeflux.orderservice;

import com.kubeflux.orderservice.kafka.OrderEventProducer;
import com.kubeflux.orderservice.model.Order;
import com.kubeflux.orderservice.repository.OrderRepository;
import com.kubeflux.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEventProducer eventProducer;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_savesOrderWithCreatedStatus() {
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            return o;
        });

        Order result = orderService.createOrder("laptop-1", 2, "cust-123");

        assertEquals("laptop-1", result.getProductId());
        assertEquals(2, result.getQuantity());
        assertEquals(Order.OrderStatus.CREATED, result.getStatus());
        verify(eventProducer, times(1)).publishOrderCreated(any());
    }

    @Test
    void createOrder_publishesEventAfterSaving() {
        when(orderRepository.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        orderService.createOrder("laptop-2", 1, "cust-456");

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(eventProducer, times(1)).publishOrderCreated(any());
    }
}