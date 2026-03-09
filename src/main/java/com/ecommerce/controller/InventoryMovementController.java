package com.ecommerce.controller;

import com.ecommerce.dto.InventoryMovementRequest;
import com.ecommerce.dto.InventoryMovementResponse;
import com.ecommerce.service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final InventoryMovementService service;

    @GetMapping
    public List<InventoryMovementResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public InventoryMovementResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryMovementResponse create(@RequestBody InventoryMovementRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public InventoryMovementResponse update(@PathVariable Long id, @RequestBody InventoryMovementRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
