package com.ecommerce.controller;

import com.ecommerce.dto.WarrantyRequest;
import com.ecommerce.dto.WarrantyResponse;
import com.ecommerce.service.WarrantyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post-Sales Domain")
@RestController
@RequestMapping("/api/warranties")
@RequiredArgsConstructor
public class WarrantyController {

    private final WarrantyService service;

    @GetMapping
    public List<WarrantyResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public WarrantyResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarrantyResponse create(@RequestBody WarrantyRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public WarrantyResponse update(@PathVariable Long id, @RequestBody WarrantyRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
