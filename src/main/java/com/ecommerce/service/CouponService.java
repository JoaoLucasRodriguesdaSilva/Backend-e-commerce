package com.ecommerce.service;

import com.ecommerce.dto.CouponApplyRequest;
import com.ecommerce.dto.CouponApplyResponse;
import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.CouponResponse;
import com.ecommerce.entity.Coupon;
import com.ecommerce.entity.Product;
import com.ecommerce.enums.CouponType;
import com.ecommerce.repository.CouponRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;
    private final ProductRepository productRepository;

    public List<CouponResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CouponResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public CouponResponse create(CouponRequest dto) {
        Coupon entity = Coupon.builder()
            .code(dto.code())
            .type(dto.type())
            .value(dto.value())
            .maxUsage(dto.maxUsage())
            .currentUsage(dto.currentUsage())
            .expiresAt(dto.expiresAt())
            .minimumOrder(dto.minimumOrder())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public CouponResponse update(Long id, CouponRequest dto) {
        Coupon entity = getOrThrow(id);
        entity.setCode(dto.code());
        entity.setType(dto.type());
        entity.setValue(dto.value());
        entity.setMaxUsage(dto.maxUsage());
        entity.setCurrentUsage(dto.currentUsage());
        entity.setExpiresAt(dto.expiresAt());
        entity.setMinimumOrder(dto.minimumOrder());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    @Transactional
    public CouponApplyResponse applyCoupon(CouponApplyRequest dto) {
        Coupon coupon = repository.findByCode(dto.code())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found: " + dto.code()));

        if (coupon.getExpiresAt() != null && coupon.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Coupon has expired");
        }

        if (coupon.getMaxUsage() != null && coupon.getCurrentUsage() != null
                && coupon.getCurrentUsage() >= coupon.getMaxUsage()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Coupon usage limit reached");
        }

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + dto.productId()));

        BigDecimal originalPrice = product.getBasePrice();

        if (coupon.getMinimumOrder() != null && originalPrice.compareTo(coupon.getMinimumOrder()) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Product price does not meet the minimum order requirement of " + coupon.getMinimumOrder());
        }

        BigDecimal discountAmount;
        if (coupon.getType() == CouponType.PERCENTAGE) {
            discountAmount = originalPrice.multiply(coupon.getValue())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        } else {
            discountAmount = coupon.getValue().min(originalPrice);
        }

        BigDecimal finalPrice = originalPrice.subtract(discountAmount).max(BigDecimal.ZERO);

        if (coupon.getCurrentUsage() != null) {
            coupon.setCurrentUsage(coupon.getCurrentUsage() + 1);
        } else {
            coupon.setCurrentUsage(1);
        }
        repository.save(coupon);

        return new CouponApplyResponse(originalPrice, discountAmount, finalPrice);
    }

    private Coupon getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found: " + id));
    }

    private CouponResponse toResponse(Coupon e) {
        return new CouponResponse(e.getId(), e.getCode(), e.getType(), e.getValue(), e.getMaxUsage(),
            e.getCurrentUsage(), e.getExpiresAt(), e.getMinimumOrder());
    }
}
