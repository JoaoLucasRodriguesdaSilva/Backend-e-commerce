package com.ecommerce.dto;

import java.time.LocalDateTime;

public record SessionResponse(
    Long id,
    Long customerId,
    String token,
    LocalDateTime expiresAt,
    String ip,
    String userAgent
) {}
