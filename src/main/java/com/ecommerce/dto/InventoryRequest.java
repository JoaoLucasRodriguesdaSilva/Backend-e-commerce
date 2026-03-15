package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating or updating an inventory record")
public record InventoryRequest(
    @Schema(description = "ID of the product variant this inventory entry tracks", example = "5")
    Long variantId,

    @Schema(description = "Total stock quantity on hand", example = "150")
    Integer quantity,

    @Schema(description = "Quantity reserved for pending orders", example = "10")
    Integer reservedQuantity,

    @Schema(description = "Minimum quantity threshold that triggers a reorder", example = "20")
    Integer reorderPoint
) {}
