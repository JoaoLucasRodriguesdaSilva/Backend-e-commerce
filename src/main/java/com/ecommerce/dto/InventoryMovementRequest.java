package com.ecommerce.dto;

import com.ecommerce.enums.MovementType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for recording an inventory movement")
public record InventoryMovementRequest(
    @Schema(description = "ID of the product variant affected", example = "5")
    Long variantId,

    @Schema(description = "Movement type: IN (stock received), OUT (stock dispatched), ADJUSTMENT", example = "IN")
    MovementType type,

    @Schema(description = "Quantity moved (positive integer)", example = "50")
    Integer quantity,

    @Schema(description = "Reason or reference for the movement", example = "Purchase order PO-2024-001")
    String reason
) {}
