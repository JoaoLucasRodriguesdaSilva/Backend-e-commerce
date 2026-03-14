package com.ecommerce.controller;

import com.ecommerce.dto.SavedCardTokenRequest;
import com.ecommerce.dto.SavedCardTokenResponse;
import com.ecommerce.service.SavedCardTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SavedCardToken")
@RestController
@RequestMapping("/api/saved-card-tokens")
@RequiredArgsConstructor
public class SavedCardTokenController {

    private final SavedCardTokenService service;

    @Operation(summary = "List all saved card tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saved card tokens retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public List<SavedCardTokenResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get saved card token by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saved card token found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Saved card token not found")
    })
    @GetMapping("/{id}")
    public SavedCardTokenResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new saved card token")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Saved card token created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SavedCardTokenResponse create(@RequestBody SavedCardTokenRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a saved card token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saved card token updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Saved card token not found")
    })
    @PutMapping("/{id}")
    public SavedCardTokenResponse update(@PathVariable Long id, @RequestBody SavedCardTokenRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a saved card token")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Saved card token deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Saved card token not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
