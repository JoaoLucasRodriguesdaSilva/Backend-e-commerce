package com.ecommerce.controller;

import com.ecommerce.dto.VariantRequest;
import com.ecommerce.dto.VariantResponse;
import com.ecommerce.service.VariantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Variant")
@RestController
@RequestMapping("/api/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService service;

    @Operation(summary = "List all variants")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variants retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<VariantResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get variant by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variant found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Variant not found")
    })
    @GetMapping("/{id}")
    public VariantResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new variant")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Variant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VariantResponse create(@RequestBody VariantRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a variant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Variant updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Variant not found")
    })
    @PutMapping("/{id}")
    public VariantResponse update(@PathVariable Long id, @RequestBody VariantRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a variant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Variant deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Variant not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
