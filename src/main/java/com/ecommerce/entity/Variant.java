package com.ecommerce.entity;

import com.ecommerce.enums.VariantAttribute;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VariantAttribute attribute;

    @Column(name = "variant_value", nullable = false)
    private String value;

    private BigDecimal extraPrice;

    private String variantSku;
}
