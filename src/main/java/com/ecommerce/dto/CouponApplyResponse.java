package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Result of applying a coupon to a product price")
public record CouponApplyResponse(
        @Schema(description = "Original product price before discount", example = "249.99")
        BigDecimal originalPrice,

        @Schema(description = "Discount amount calculated", example = "25.00")
        BigDecimal discountAmount,

        @Schema(description = "Final price after discount", example = "224.99")
        BigDecimal finalPrice
) {}
