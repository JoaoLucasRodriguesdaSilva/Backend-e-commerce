package com.ecommerce.entity;

import com.ecommerce.enums.CouponType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;

    @Column(name = "coupon_value", nullable = false)
    private BigDecimal value;

    private Integer maxUsage;

    private Integer currentUsage;

    private LocalDateTime expiresAt;

    private BigDecimal minimumOrder;
}
