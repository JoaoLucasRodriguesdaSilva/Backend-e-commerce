package com.ecommerce.dto;

import java.time.LocalDateTime;

public record SessionRequest(
    Long customerId,
    String token,
    LocalDateTime expiresAt,
    String ip,
    String userAgent
) {}
