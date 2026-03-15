package com.ecommerce.dto;

import com.ecommerce.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Inventory movement record returned by the API")
public record InventoryMovementResponse(
    @Schema(description = "Unique identifier of the movement record", example = "1")
    Long id,

    @Schema(description = "ID of the product variant affected", example = "5")
    Long variantId,

    @Schema(description = "Movement type: IN, OUT, ADJUSTMENT", example = "IN")
    MovementType type,

    @Schema(description = "Quantity moved", example = "50")
    Integer quantity,

    @Schema(description = "Reason or reference for the movement", example = "Purchase order PO-2024-001")
    String reason,

    @Schema(description = "Timestamp when the movement was recorded (ISO 8601)", example = "2024-01-15T09:00:00")
    LocalDateTime createdAt
) {}
