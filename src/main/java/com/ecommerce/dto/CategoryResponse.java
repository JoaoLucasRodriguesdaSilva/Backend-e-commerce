package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category resource returned by the API")
public record CategoryResponse(
    @Schema(description = "Unique identifier of the category", example = "1")
    Long id,

    @Schema(description = "Category display name", example = "Electronics")
    String name,

    @Schema(description = "URL-friendly slug (unique)", example = "electronics")
    String slug,

    @Schema(description = "ID of the parent category (null for top-level)", example = "1")
    Long parentCategoryId,

    @Schema(description = "Icon identifier or URL for the category", example = "icon-electronics")
    String icon,

    @Schema(description = "Sort order used for display", example = "10")
    Integer displayOrder
) {}
