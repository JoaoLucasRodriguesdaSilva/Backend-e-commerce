package com.ecommerce.controller;

import com.ecommerce.dto.CouponApplyRequest;
import com.ecommerce.dto.CouponApplyResponse;
import com.ecommerce.dto.CouponRequest;
import com.ecommerce.dto.CouponResponse;
import com.ecommerce.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "List all coupons")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coupons retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<CouponResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get coupon by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coupon found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Coupon not found")
    })
    @GetMapping("/{id}")
    public CouponResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Apply a coupon code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coupon applied successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired coupon code"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/apply")
    public CouponApplyResponse apply(@RequestBody CouponApplyRequest dto) {
        return service.applyCoupon(dto);
    }

    @Operation(summary = "Create a new coupon")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Coupon created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CouponResponse create(@RequestBody CouponRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a coupon")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coupon updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Coupon not found")
    })
    @PutMapping("/{id}")
    public CouponResponse update(@PathVariable Long id, @RequestBody CouponRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a coupon")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Coupon deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Coupon not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
