package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for applying a coupon code to a product")
public record CouponApplyRequest(
        @Schema(description = "Coupon code to apply", example = "SAVE10")
        String code,

        @Schema(description = "ID of the product to apply the coupon to", example = "1")
        Long productId
) {}
