package com.ecommerce.dto;

import com.ecommerce.enums.MovementType;

public record InventoryMovementRequest(
    Long variantId,
    MovementType type,
    Integer quantity,
    String reason
) {}
