package com.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromotionRequest(
    Long productId,
    BigDecimal promotionalPrice,
    LocalDateTime startsAt,
    LocalDateTime endsAt,
    Boolean featuredHome
) {}
