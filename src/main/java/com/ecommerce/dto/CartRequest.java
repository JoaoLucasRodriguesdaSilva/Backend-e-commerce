package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for creating or updating a shopping cart")
public record CartRequest(
    @Schema(description = "ID of the customer who owns this cart", example = "1")
    Long customerId
) {}
