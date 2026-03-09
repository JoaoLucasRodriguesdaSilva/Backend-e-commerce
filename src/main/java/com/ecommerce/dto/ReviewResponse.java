package com.ecommerce.dto;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long id,
    Long productId,
    Long customerId,
    Integer rating,
    String title,
    String comment,
    Boolean verifiedPurchase,
    LocalDateTime createdAt
) {}
