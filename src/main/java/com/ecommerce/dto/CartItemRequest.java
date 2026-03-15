package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Request payload for adding or updating an item in the cart")
public record CartItemRequest(
    @Schema(description = "ID of the cart", example = "1")
    Long cartId,

    @Schema(description = "ID of the product variant to add", example = "5")
    Long variantId,

    @Schema(description = "Quantity to add", example = "1")
    Integer quantity,

    @Schema(description = "Current unit price of the variant", example = "249.99")
    BigDecimal unitPrice
) {}
