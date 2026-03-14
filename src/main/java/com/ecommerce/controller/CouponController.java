package com.ecommerce.controller;

import com.ecommerce.dto.CouponApplyRequest;
import com.ecommerce.dto.CouponApplyResponse;
import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.CouponResponse;
import com.ecommerce.service.CouponService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Coupon")
@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService service;

    @GetMapping
    public List<CouponResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CouponResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/apply")
    public CouponApplyResponse apply(@RequestBody CouponApplyRequest dto) {
        return service.applyCoupon(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CouponResponse create(@RequestBody CouponRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public CouponResponse update(@PathVariable Long id, @RequestBody CouponRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
