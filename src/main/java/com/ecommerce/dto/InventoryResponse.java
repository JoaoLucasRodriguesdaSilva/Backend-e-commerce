package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Inventory record returned by the API")
public record InventoryResponse(
    @Schema(description = "Unique identifier of the inventory record", example = "1")
    Long id,

    @Schema(description = "ID of the product variant", example = "5")
    Long variantId,

    @Schema(description = "Total stock quantity on hand", example = "150")
    Integer quantity,

    @Schema(description = "Quantity reserved for pending orders", example = "10")
    Integer reservedQuantity,

    @Schema(description = "Minimum quantity threshold that triggers a reorder", example = "20")
    Integer reorderPoint
) {}
