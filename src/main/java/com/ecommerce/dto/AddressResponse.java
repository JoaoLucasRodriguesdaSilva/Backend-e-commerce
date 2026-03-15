package com.ecommerce.dto;

import com.ecommerce.enums.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Address resource returned by the API")
public record AddressResponse(
    @Schema(description = "Unique identifier of the address", example = "1")
    Long id,

    @Schema(description = "ID of the customer who owns this address", example = "1")
    Long customerId,

    @Schema(description = "Address type: SHIPPING or BILLING", example = "SHIPPING")
    AddressType type,

    @Schema(description = "ZIP / postal code", example = "01310-100")
    String zipCode,

    @Schema(description = "Street name", example = "Avenida Paulista")
    String street,

    @Schema(description = "Street number", example = "1000")
    String number,

    @Schema(description = "Apartment, suite, unit, etc.", example = "Apt 42")
    String complement,

    @Schema(description = "Neighborhood / district", example = "Bela Vista")
    String neighborhood,

    @Schema(description = "City", example = "São Paulo")
    String city,

    @Schema(description = "State (2-letter code)", example = "SP")
    String state,

    @Schema(description = "Whether this is the default address for this type", example = "true")
    Boolean isDefault
) {}
