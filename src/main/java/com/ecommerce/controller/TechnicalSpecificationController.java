package com.ecommerce.controller;

import com.ecommerce.dto.TechnicalSpecificationRequest;
import com.ecommerce.dto.TechnicalSpecificationResponse;
import com.ecommerce.service.TechnicalSpecificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "TechnicalSpecification")
@RestController
@RequestMapping("/api/technical-specifications")
@RequiredArgsConstructor
public class TechnicalSpecificationController {

    private final TechnicalSpecificationService service;

    @Operation(summary = "List all technical specifications")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Technical specifications retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<TechnicalSpecificationResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get technical specification by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Technical specification found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Technical specification not found")
    })
    @GetMapping("/{id}")
    public TechnicalSpecificationResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new technical specification")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Technical specification created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechnicalSpecificationResponse create(@RequestBody TechnicalSpecificationRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a technical specification")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Technical specification updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Technical specification not found")
    })
    @PutMapping("/{id}")
    public TechnicalSpecificationResponse update(@PathVariable Long id, @RequestBody TechnicalSpecificationRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a technical specification")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Technical specification deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Technical specification not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
