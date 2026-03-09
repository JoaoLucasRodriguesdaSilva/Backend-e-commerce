package com.ecommerce.dto;

public record SavedCardTokenRequest(
    Long customerId,
    String last4Digits,
    String brand,
    String gatewayToken,
    String expiration
) {}
