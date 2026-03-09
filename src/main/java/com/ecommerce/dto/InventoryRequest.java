package com.ecommerce.dto;

public record InventoryRequest(
    Long variantId,
    Integer quantity,
    Integer reservedQuantity,
    Integer reorderPoint
) {}
