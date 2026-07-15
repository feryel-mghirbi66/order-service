package com.kubeflux.orderservice.kafka;

import com.kubeflux.orderservice.event.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private static final String TOPIC = "orders";

    public OrderEventProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreated(OrderCreatedEvent event) {
        // Clé = orderId, garantit que tous les événements d'une même commande
        // vont dans la même partition (ordre préservé)
        kafkaTemplate.send(TOPIC, event.getOrderId(), event);
    }
}