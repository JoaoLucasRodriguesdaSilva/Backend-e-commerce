package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String technicalDescription;

    @Column(nullable = false)
    private BigDecimal basePrice;

    private BigDecimal promotionalPrice;

    @Column(unique = true, nullable = false)
    private String sku;

    private String brand;

    private String model;

    private Integer warrantyMonths;

    private BigDecimal weight;

    private String dimensions;

    @Builder.Default
    private Boolean active = true;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
