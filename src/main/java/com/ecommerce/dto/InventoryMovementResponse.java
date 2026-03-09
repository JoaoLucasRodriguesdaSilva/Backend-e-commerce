package com.ecommerce.dto;

import com.ecommerce.enums.MovementType;
import java.time.LocalDateTime;

public record InventoryMovementResponse(
    Long id,
    Long variantId,
    MovementType type,
    Integer quantity,
    String reason,
    LocalDateTime createdAt
) {}
