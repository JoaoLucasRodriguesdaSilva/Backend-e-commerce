package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Product promotion resource returned by the API")
public record PromotionResponse(
    @Schema(description = "Unique identifier of the promotion", example = "1")
    Long id,

    @Schema(description = "ID of the promoted product", example = "1")
    Long productId,

    @Schema(description = "Promotional price during the promotion period", example = "199.99")
    BigDecimal promotionalPrice,

    @Schema(description = "Promotion start date and time (ISO 8601)", example = "2024-11-29T00:00:00")
    LocalDateTime startsAt,

    @Schema(description = "Promotion end date and time (ISO 8601)", example = "2024-11-29T23:59:59")
    LocalDateTime endsAt,

    @Schema(description = "Whether this promotion is featured on the home page", example = "true")
    Boolean featuredHome
) {}
