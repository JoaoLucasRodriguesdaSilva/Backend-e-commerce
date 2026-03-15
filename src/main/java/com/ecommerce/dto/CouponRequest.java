package com.ecommerce.dto;

import com.ecommerce.enums.CouponType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Request payload for creating or updating a coupon")
public record CouponRequest(
    @Schema(description = "Unique coupon code", example = "SAVE10")
    String code,

    @Schema(description = "Discount type: PERCENTAGE or FIXED_AMOUNT", example = "PERCENTAGE")
    CouponType type,

    @Schema(description = "Discount value (percentage or fixed amount depending on type)", example = "10.00")
    BigDecimal value,

    @Schema(description = "Maximum number of times this coupon can be used", example = "100")
    Integer maxUsage,

    @Schema(description = "Current number of times this coupon has been used", example = "0")
    Integer currentUsage,

    @Schema(description = "Expiry date and time (ISO 8601)", example = "2024-12-31T23:59:59")
    LocalDateTime expiresAt,

    @Schema(description = "Minimum order amount required to use this coupon", example = "50.00")
    BigDecimal minimumOrder
) {}
