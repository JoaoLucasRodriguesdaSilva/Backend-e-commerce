package com.ecommerce.dto;

import java.math.BigDecimal;

public record CartItemRequest(
    Long cartId,
    Long variantId,
    Integer quantity,
    BigDecimal unitPrice
) {}
