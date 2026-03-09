package com.ecommerce.dto;

import com.ecommerce.enums.ReturnStatus;
import com.ecommerce.enums.ReturnType;
import java.time.LocalDateTime;

public record OrderReturnResponse(
    Long id,
    Long orderId,
    String reason,
    ReturnStatus status,
    ReturnType type,
    LocalDateTime approvedAt
) {}
