package com.ecommerce.dto;

import com.ecommerce.enums.CouponType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Coupon resource returned by the API")
public record CouponResponse(
    @Schema(description = "Unique identifier of the coupon", example = "1")
    Long id,

    @Schema(description = "Unique coupon code", example = "SAVE10")
    String code,

    @Schema(description = "Discount type: PERCENTAGE or FIXED_AMOUNT", example = "PERCENTAGE")
    CouponType type,

    @Schema(description = "Discount value", example = "10.00")
    BigDecimal value,

    @Schema(description = "Maximum number of uses allowed", example = "100")
    Integer maxUsage,

    @Schema(description = "Current number of times used", example = "5")
    Integer currentUsage,

    @Schema(description = "Expiry date and time (ISO 8601)", example = "2024-12-31T23:59:59")
    LocalDateTime expiresAt,

    @Schema(description = "Minimum order amount required", example = "50.00")
    BigDecimal minimumOrder
) {}
