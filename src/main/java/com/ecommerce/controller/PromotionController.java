package com.ecommerce.controller;

import com.ecommerce.dto.PromotionRequest;
import com.ecommerce.dto.PromotionResponse;
import com.ecommerce.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService service;

    @GetMapping
    public List<PromotionResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PromotionResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionResponse create(@RequestBody PromotionRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PromotionResponse update(@PathVariable Long id, @RequestBody PromotionRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
