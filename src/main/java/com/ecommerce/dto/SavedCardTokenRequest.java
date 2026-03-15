package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request payload for saving a card payment token")
public record SavedCardTokenRequest(
    @Schema(description = "ID of the customer who owns this card", example = "1")
    Long customerId,

    @Schema(description = "Last 4 digits of the card number", example = "4242")
    String last4Digits,

    @Schema(description = "Card brand (e.g. Visa, Mastercard)", example = "Visa")
    String brand,

    @Schema(description = "Tokenized card reference from the payment gateway", example = "tok_visa_4242")
    String gatewayToken,

    @Schema(description = "Card expiration date in MM/YY format", example = "12/27")
    String expiration
) {}
