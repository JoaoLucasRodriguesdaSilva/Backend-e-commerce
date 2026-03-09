package com.ecommerce.controller;

import com.ecommerce.dto.OrderReturnRequest;
import com.ecommerce.dto.OrderReturnResponse;
import com.ecommerce.service.OrderReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-returns")
@RequiredArgsConstructor
public class OrderReturnController {

    private final OrderReturnService service;

    @GetMapping
    public List<OrderReturnResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderReturnResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderReturnResponse create(@RequestBody OrderReturnRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public OrderReturnResponse update(@PathVariable Long id, @RequestBody OrderReturnRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
