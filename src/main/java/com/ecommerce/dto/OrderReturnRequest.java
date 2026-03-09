package com.ecommerce.dto;

import com.ecommerce.enums.ReturnStatus;
import com.ecommerce.enums.ReturnType;
import java.time.LocalDateTime;

public record OrderReturnRequest(
    Long orderId,
    String reason,
    ReturnStatus status,
    ReturnType type,
    LocalDateTime approvedAt
) {}
