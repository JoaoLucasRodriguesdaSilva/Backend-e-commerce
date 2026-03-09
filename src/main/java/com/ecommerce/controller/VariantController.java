package com.ecommerce.controller;

import com.ecommerce.dto.VariantRequest;
import com.ecommerce.dto.VariantResponse;
import com.ecommerce.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService service;

    @GetMapping
    public List<VariantResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public VariantResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VariantResponse create(@RequestBody VariantRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public VariantResponse update(@PathVariable Long id, @RequestBody VariantRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
