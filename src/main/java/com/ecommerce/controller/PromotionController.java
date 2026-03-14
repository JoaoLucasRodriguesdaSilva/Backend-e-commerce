package com.ecommerce.controller;

import com.ecommerce.dto.PromotionRequest;
import com.ecommerce.dto.PromotionResponse;
import com.ecommerce.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Promotion")
@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService service;

    @Operation(summary = "List all promotions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promotions retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<PromotionResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get promotion by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promotion found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Promotion not found")
    })
    @GetMapping("/{id}")
    public PromotionResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new promotion")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Promotion created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromotionResponse create(@RequestBody PromotionRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a promotion")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promotion updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Promotion not found")
    })
    @PutMapping("/{id}")
    public PromotionResponse update(@PathVariable Long id, @RequestBody PromotionRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a promotion")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Promotion deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Promotion not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
