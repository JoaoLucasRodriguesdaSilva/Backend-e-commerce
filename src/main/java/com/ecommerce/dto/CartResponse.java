package com.ecommerce.dto;

import java.time.LocalDateTime;

public record CartResponse(
    Long id,
    Long customerId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
