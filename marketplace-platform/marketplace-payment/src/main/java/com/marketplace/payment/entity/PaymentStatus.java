package com.marketplace.payment.entity;

public enum PaymentStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED,
    REFUNDED,
    HELD_IN_ESCROW,
    RELEASED
}