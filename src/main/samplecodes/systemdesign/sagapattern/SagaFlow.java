public void createOrder(OrderRequest req) {
    orderRepository.save(new Order(...));
    kafka.send("order-created", new OrderCreatedEvent(req.getOrderId()));
}

@KafkaListener(topics = "order-created")
public void onOrderCreated(OrderCreatedEvent event) {
    try {
        paymentProcessor.charge(event.getOrderId());
        kafka.send("payment-processed", new PaymentProcessedEvent(event.getOrderId()));
    } catch (Exception e) {
        kafka.send("payment-failed", new PaymentFailedEvent(event.getOrderId()));
    }
}

@KafkaListener(topics = "payment-processed")
public void onPaymentProcessed(PaymentProcessedEvent event) {
    try {
        inventoryService.deduct(event.getOrderId());
        kafka.send("inventory-updated", new InventoryUpdatedEvent(event.getOrderId()));
    } catch (Exception e) {
        kafka.send("inventory-update-failed", new InventoryUpdateFailedEvent(event.getOrderId()));
    }
}

// Compensation Logic
@KafkaListener(topics = "payment-failed")
public void onPaymentFailed(PaymentFailedEvent event) {
    orderService.cancel(event.getOrderId());
}

@KafkaListener(topics = "inventory-update-failed")
public void onInventoryUpdateFailed(InventoryUpdateFailedEvent event) {
    paymentProcessor.refund(event.getOrderId());
}

