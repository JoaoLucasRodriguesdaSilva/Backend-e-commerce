package com.ecommerce.dto;

public record InventoryResponse(
    Long id,
    Long variantId,
    Integer quantity,
    Integer reservedQuantity,
    Integer reorderPoint
) {}
