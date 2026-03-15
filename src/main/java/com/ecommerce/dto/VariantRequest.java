package com.ecommerce.dto;

import com.ecommerce.enums.VariantAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Request payload for creating or updating a product variant")
public record VariantRequest(
    @Schema(description = "ID of the product this variant belongs to", example = "1")
    Long productId,

    @Schema(description = "Variant attribute type: COLOR, SIZE, MATERIAL, WEIGHT, CAPACITY", example = "COLOR")
    VariantAttribute attribute,

    @Schema(description = "Value for the variant attribute", example = "Space Gray")
    String value,

    @Schema(description = "Additional price on top of the base product price", example = "0.00")
    BigDecimal extraPrice,

    @Schema(description = "Unique SKU for this specific variant", example = "WHP-PRO-001-GRY")
    String variantSku
) {}
