package com.ecommerce.dto;

public record ReviewRequest(
    Long productId,
    Long customerId,
    Integer rating,
    String title,
    String comment,
    Boolean verifiedPurchase
) {}
