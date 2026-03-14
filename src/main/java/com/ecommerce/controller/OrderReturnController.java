package com.ecommerce.controller;

import com.ecommerce.dto.OrderReturnRequest;
import com.ecommerce.dto.OrderReturnResponse;
import com.ecommerce.service.OrderReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OrderReturn")
@RestController
@RequestMapping("/api/order-returns")
@RequiredArgsConstructor
public class OrderReturnController {

    private final OrderReturnService service;

    @Operation(summary = "List all order returns belonging to the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order returns retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/my")
    public List<OrderReturnResponse> findMy(@AuthenticationPrincipal UserDetails user) {
        return service.findAllByEmail(user.getUsername());
    }

    @Operation(summary = "Get a specific order return belonging to the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order return found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Order return not found")
    })
    @GetMapping("/my/{id}")
    public OrderReturnResponse findMyById(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return service.findByIdAndEmail(id, user.getUsername());
    }

    @Operation(summary = "Create an order return for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order return created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/my")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderReturnResponse createMy(@RequestBody OrderReturnRequest dto,
                                        @AuthenticationPrincipal UserDetails user) {
        return service.createForUser(dto, user.getUsername());
    }

    @Operation(summary = "List all order returns")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order returns retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public List<OrderReturnResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get order return by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order return found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Order return not found")
    })
    @GetMapping("/{id}")
    public OrderReturnResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new order return")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order return created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderReturnResponse create(@RequestBody OrderReturnRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update an order return")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order return updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Order return not found")
    })
    @PutMapping("/{id}")
    public OrderReturnResponse update(@PathVariable Long id, @RequestBody OrderReturnRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete an order return")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order return deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Order return not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
