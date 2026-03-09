package com.ecommerce.dto;

public record SavedCardTokenResponse(
    Long id,
    Long customerId,
    String last4Digits,
    String brand,
    String gatewayToken,
    String expiration
) {}
