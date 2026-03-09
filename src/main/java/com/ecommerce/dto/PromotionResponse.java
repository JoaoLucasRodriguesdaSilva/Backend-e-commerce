package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromotionResponse(
    Long id,
    Long productId,
    BigDecimal promotionalPrice,
    LocalDateTime startsAt,
    LocalDateTime endsAt,
    Boolean featuredHome
) {}
