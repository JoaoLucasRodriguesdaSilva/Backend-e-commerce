package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product image resource returned by the API")
public record ProductImageResponse(
    @Schema(description = "Unique identifier of the image", example = "1")
    Long id,

    @Schema(description = "ID of the parent product", example = "1")
    Long productId,

    @Schema(description = "Public URL of the image", example = "https://cdn.example.com/products/wph-pro-001.jpg")
    String url,

    @Schema(description = "Alternate text for accessibility / SEO", example = "Wireless Headphones Pro – front view")
    String alt,

    @Schema(description = "Sort order for gallery display", example = "1")
    Integer displayOrder,

    @Schema(description = "Whether this is the primary / thumbnail image", example = "true")
    Boolean isPrimary
) {}
