package com.ecommerce.dto;

import java.math.BigDecimal;

public record OrderItemRequest(
    Long orderId,
    Long variantId,
    Integer quantity,
    BigDecimal unitPrice,
    String nameSnapshot,
    String skuSnapshot
) {}
