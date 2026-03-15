package com.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Request payload for creating or updating a customer session")
public record SessionRequest(
    @Schema(description = "ID of the customer this session belongs to", example = "1")
    Long customerId,

    @Schema(description = "Session token (unique)", example = "sess_abc123xyz")
    String token,

    @Schema(description = "Session expiry date and time (ISO 8601)", example = "2024-01-22T10:30:00")
    LocalDateTime expiresAt,

    @Schema(description = "IP address of the client", example = "192.168.1.100")
    String ip,

    @Schema(description = "User-Agent string of the client browser/app", example = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
    String userAgent
) {}
