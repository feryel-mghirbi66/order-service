package com.kubeflux.orderservice.event;

import java.time.Instant;
import java.util.UUID;

public class OrderCreatedEvent {
    private String eventId;
    private Integer eventVersion = 1;
    private String orderId;
    private String productId;
    private Integer quantity;
    private String customerId;
    private Instant timestamp;

    public OrderCreatedEvent(UUID orderId, String productId, Integer quantity, String customerId) {
        this.eventId = UUID.randomUUID().toString();
        this.orderId = orderId.toString();
        this.productId = productId;
        this.quantity = quantity;
        this.customerId = customerId;
        this.timestamp = Instant.now();
    }

    // Getters (nécessaires pour la sérialisation JSON)
    public String getEventId() { return eventId; }
    public Integer getEventVersion() { return eventVersion; }
    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public String getCustomerId() { return customerId; }
    public Instant getTimestamp() { return timestamp; }
}