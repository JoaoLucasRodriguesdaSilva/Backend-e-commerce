package com.ecommerce.dto;

import com.ecommerce.enums.CouponType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponse(
    Long id,
    String code,
    CouponType type,
    BigDecimal value,
    Integer maxUsage,
    Integer currentUsage,
    LocalDateTime expiresAt,
    BigDecimal minimumOrder
) {}
