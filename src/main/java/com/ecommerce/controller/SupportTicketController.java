package com.ecommerce.controller;

import com.ecommerce.dto.SupportTicketRequest;
import com.ecommerce.dto.SupportTicketResponse;
import com.ecommerce.service.SupportTicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post-Sales Domain")
@RestController
@RequestMapping("/api/support-tickets")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService service;

    @GetMapping
    public List<SupportTicketResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SupportTicketResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupportTicketResponse create(@RequestBody SupportTicketRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SupportTicketResponse update(@PathVariable Long id, @RequestBody SupportTicketRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
