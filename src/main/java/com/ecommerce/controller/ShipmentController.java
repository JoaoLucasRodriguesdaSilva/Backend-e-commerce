package com.ecommerce.controller;

import com.ecommerce.dto.ShipmentRequest;
import com.ecommerce.dto.ShipmentResponse;
import com.ecommerce.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService service;

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
