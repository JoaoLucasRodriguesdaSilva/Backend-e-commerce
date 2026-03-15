package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Review resource returned by the API")
public record ReviewResponse(
    @Schema(description = "Unique identifier of the review", example = "1")
    Long id,

    @Schema(description = "ID of the reviewed product", example = "1")
    Long productId,

    @Schema(description = "ID of the customer who wrote the review", example = "1")
    Long customerId,

    @Schema(description = "Rating from 1 (worst) to 5 (best)", example = "5")
    Integer rating,

    @Schema(description = "Short review title", example = "Excellent sound quality!")
    String title,

    @Schema(description = "Detailed review comment", example = "Best headphones I have ever used. ANC is incredible.")
    String comment,

    @Schema(description = "Whether this is a verified purchase review", example = "true")
    Boolean verifiedPurchase,

    @Schema(description = "Timestamp when the review was submitted (ISO 8601)", example = "2024-01-20T15:00:00")
    LocalDateTime createdAt
) {}
