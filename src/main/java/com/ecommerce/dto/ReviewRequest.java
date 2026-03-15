package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating or updating a product review")
public record ReviewRequest(
    @Schema(description = "ID of the product being reviewed", example = "1")
    Long productId,

    @Schema(description = "ID of the customer writing the review", example = "1")
    Long customerId,

    @Schema(description = "Rating from 1 (worst) to 5 (best)", example = "5")
    Integer rating,

    @Schema(description = "Short review title", example = "Excellent sound quality!")
    String title,

    @Schema(description = "Detailed review comment", example = "Best headphones I have ever used. ANC is incredible.")
    String comment,

    @Schema(description = "Whether the reviewer has confirmed purchase of this product", example = "true")
    Boolean verifiedPurchase
) {}
