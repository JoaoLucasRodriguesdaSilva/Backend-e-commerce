package com.ecommerce.dto;

import java.math.BigDecimal;

public record CouponApplyResponse(
        BigDecimal originalPrice,
        BigDecimal discountAmount,
        BigDecimal finalPrice
) {}
