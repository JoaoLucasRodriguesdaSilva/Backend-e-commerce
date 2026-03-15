package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Request payload for creating or updating an order item")
public record OrderItemRequest(
    @Schema(description = "ID of the order this item belongs to", example = "1")
    Long orderId,

    @Schema(description = "ID of the product variant", example = "5")
    Long variantId,

    @Schema(description = "Quantity ordered", example = "2")
    Integer quantity,

    @Schema(description = "Unit price captured at purchase time", example = "249.99")
    BigDecimal unitPrice,

    @Schema(description = "Product name snapshot at purchase time", example = "Wireless Headphones Pro")
    String nameSnapshot,

    @Schema(description = "Variant SKU snapshot at purchase time", example = "WHP-PRO-001-GRY")
    String skuSnapshot
) {}
