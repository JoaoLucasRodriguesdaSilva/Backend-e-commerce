package com.ecommerce.dto;

import java.math.BigDecimal;

public record CartItemResponse(
    Long id,
    Long cartId,
    Long variantId,
    Integer quantity,
    BigDecimal unitPrice
) {}
