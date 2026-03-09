package com.ecommerce.entity;

import com.ecommerce.enums.ReturnStatus;
import com.ecommerce.enums.ReturnType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_return")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status;

    @Enumerated(EnumType.STRING)
    private ReturnType type;

    private LocalDateTime approvedAt;
}
