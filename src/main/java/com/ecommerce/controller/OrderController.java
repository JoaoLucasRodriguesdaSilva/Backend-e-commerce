package com.ecommerce.controller;

import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/my")
    public List<OrderResponse> findMy(@AuthenticationPrincipal UserDetails user) {
        return service.findAllByEmail(user.getUsername());
    }

    @GetMapping("/my/{id}")
    public OrderResponse findMyById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return service.findByIdAndEmail(id, user.getUsername());
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody OrderRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public OrderResponse update(@PathVariable Long id, @RequestBody OrderRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
