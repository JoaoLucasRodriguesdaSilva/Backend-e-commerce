package com.ecommerce.dto;

public record CouponApplyRequest(
        String code,
        Long productId
) {}
