package com.ecommerce.service;

import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.CouponResponse;
import com.ecommerce.entity.Coupon;
import com.ecommerce.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository repository;

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

    private Coupon getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coupon not found: " + id));
    }

    private CouponResponse toResponse(Coupon e) {
        return new CouponResponse(e.getId(), e.getCode(), e.getType(), e.getValue(), e.getMaxUsage(),
            e.getCurrentUsage(), e.getExpiresAt(), e.getMinimumOrder());
    }
}
