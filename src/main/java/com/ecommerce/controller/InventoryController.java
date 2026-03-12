package com.ecommerce.controller;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.dto.InventoryResponse;
import com.ecommerce.service.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory")
@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping
    public List<InventoryResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public InventoryResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse create(@RequestBody InventoryRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public InventoryResponse update(@PathVariable Long id, @RequestBody InventoryRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
