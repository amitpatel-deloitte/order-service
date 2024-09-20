package com.hashedin.order_service.model;

public enum OrderStatus {
    PENDING,
    PLACED ,
    PAYMENT_FAILED,
    ACCEPTED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED
}
