package com.ecommerce.dto;

import java.time.LocalDateTime;

public record ReviewRequest(
    Long productId,
    Long customerId,
    Integer rating,
    String title,
    String comment,
    Boolean verifiedPurchase
) {}
