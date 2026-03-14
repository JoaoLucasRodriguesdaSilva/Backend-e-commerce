package com.ecommerce.controller;

import com.ecommerce.dto.InventoryMovementRequest;
import com.ecommerce.dto.InventoryMovementResponse;
import com.ecommerce.service.InventoryMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "InventoryMovement")
@RestController
@RequestMapping("/api/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final InventoryMovementService service;

    @Operation(summary = "List all inventory movements")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory movements retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public List<InventoryMovementResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get inventory movement by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory movement found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inventory movement not found")
    })
    @GetMapping("/{id}")
    public InventoryMovementResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new inventory movement")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inventory movement created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryMovementResponse create(@RequestBody InventoryMovementRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update an inventory movement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inventory movement updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inventory movement not found")
    })
    @PutMapping("/{id}")
    public InventoryMovementResponse update(@PathVariable Long id, @RequestBody InventoryMovementRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete an inventory movement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventory movement deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Inventory movement not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
