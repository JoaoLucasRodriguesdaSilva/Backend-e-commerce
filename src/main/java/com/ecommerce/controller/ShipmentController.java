package com.ecommerce.controller;

import com.ecommerce.dto.ShipmentRequest;
import com.ecommerce.dto.ShipmentResponse;
import com.ecommerce.service.ShipmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Shipment")
@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService service;

    @GetMapping("/my")
    public List<ShipmentResponse> findMy(@AuthenticationPrincipal UserDetails user) {
        return service.findAllByEmail(user.getUsername());
    }

    @GetMapping("/my/{id}")
    public ShipmentResponse findMyById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return service.findByIdAndEmail(id, user.getUsername());
    }

    @GetMapping
    public List<ShipmentResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ShipmentResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShipmentResponse create(@RequestBody ShipmentRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ShipmentResponse update(@PathVariable Long id, @RequestBody ShipmentRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
