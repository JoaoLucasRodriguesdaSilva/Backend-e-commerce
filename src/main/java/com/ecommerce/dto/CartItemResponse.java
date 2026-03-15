package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Cart item resource returned by the API")
public record CartItemResponse(
    @Schema(description = "Unique identifier of the cart item", example = "1")
    Long id,

    @Schema(description = "ID of the parent cart", example = "1")
    Long cartId,

    @Schema(description = "ID of the product variant", example = "5")
    Long variantId,

    @Schema(description = "Quantity in the cart", example = "1")
    Integer quantity,

    @Schema(description = "Unit price at time of adding to cart", example = "249.99")
    BigDecimal unitPrice
) {}
