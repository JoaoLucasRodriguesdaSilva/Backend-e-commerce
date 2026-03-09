package com.ecommerce.entity;

import com.ecommerce.enums.ShipmentService;
import com.ecommerce.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String carrier;

    @Enumerated(EnumType.STRING)
    private ShipmentService service;

    private String trackingCode;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private BigDecimal cost;

    private Integer estimatedDays;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;
}
