package com.ecommerce.dto;

import com.ecommerce.enums.VariantAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Product variant resource returned by the API")
public record VariantResponse(
    @Schema(description = "Unique identifier of the variant", example = "1")
    Long id,

    @Schema(description = "ID of the parent product", example = "1")
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
