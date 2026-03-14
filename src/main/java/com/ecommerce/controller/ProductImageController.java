package com.ecommerce.controller;

import com.ecommerce.dto.ProductImageRequest;
import com.ecommerce.dto.ProductImageResponse;
import com.ecommerce.service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductImage")
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService service;

    @Operation(summary = "List all product images")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product images retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public List<ProductImageResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get product image by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product image found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Product image not found")
    })
    @GetMapping("/{id}")
    public ProductImageResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new product image")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product image created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductImageResponse create(@RequestBody ProductImageRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a product image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product image updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product image not found")
    })
    @PutMapping("/{id}")
    public ProductImageResponse update(@PathVariable Long id, @RequestBody ProductImageRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a product image")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product image deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Product image not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
