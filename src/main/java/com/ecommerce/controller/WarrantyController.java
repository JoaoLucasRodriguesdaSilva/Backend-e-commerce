package com.ecommerce.controller;

import com.ecommerce.dto.WarrantyRequest;
import com.ecommerce.dto.WarrantyResponse;
import com.ecommerce.service.WarrantyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Warranty")
@RestController
@RequestMapping("/api/warranties")
@RequiredArgsConstructor
public class WarrantyController {

    private final WarrantyService service;

    @Operation(summary = "List all warranties")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Warranties retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<WarrantyResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get warranty by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Warranty found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Warranty not found")
    })
    @GetMapping("/{id}")
    public WarrantyResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new warranty")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Warranty created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WarrantyResponse create(@RequestBody WarrantyRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a warranty")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Warranty updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Warranty not found")
    })
    @PutMapping("/{id}")
    public WarrantyResponse update(@PathVariable Long id, @RequestBody WarrantyRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a warranty")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Warranty deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Warranty not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
