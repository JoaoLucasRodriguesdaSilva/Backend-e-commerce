package com.ecommerce.controller;

import com.ecommerce.dto.SupportTicketRequest;
import com.ecommerce.dto.SupportTicketResponse;
import com.ecommerce.service.SupportTicketService;
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

@Tag(name = "SupportTicket")
@RestController
@RequestMapping("/api/support-tickets")
@RequiredArgsConstructor
public class SupportTicketController {

    private final SupportTicketService service;

    @Operation(summary = "List all support tickets belonging to the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Support tickets retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/my")
    public List<SupportTicketResponse> findMy(@AuthenticationPrincipal UserDetails user) {
        return service.findAllByEmail(user.getUsername());
    }

    @Operation(summary = "Get a specific support ticket belonging to the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Support ticket found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Support ticket not found")
    })
    @GetMapping("/my/{id}")
    public SupportTicketResponse findMyById(@PathVariable Long id,
                                            @AuthenticationPrincipal UserDetails user) {
        return service.findByIdAndEmail(id, user.getUsername());
    }

    @Operation(summary = "Create a support ticket for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Support ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/my")
    @ResponseStatus(HttpStatus.CREATED)
    public SupportTicketResponse createMy(@RequestBody SupportTicketRequest dto,
                                          @AuthenticationPrincipal UserDetails user) {
        return service.createForUser(dto, user.getUsername());
    }

    @Operation(summary = "Delete a support ticket belonging to the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Support ticket deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Support ticket not found")
    })
    @DeleteMapping("/my/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMy(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        service.deleteByIdAndEmail(id, user.getUsername());
    }

    @Operation(summary = "List all support tickets")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Support tickets retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public List<SupportTicketResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get support ticket by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Support ticket found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Support ticket not found")
    })
    @GetMapping("/{id}")
    public SupportTicketResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new support ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Support ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupportTicketResponse create(@RequestBody SupportTicketRequest dto) {
        return service.create(dto);
    }

    @Operation(summary = "Update a support ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Support ticket updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Support ticket not found")
    })
    @PutMapping("/{id}")
    public SupportTicketResponse update(@PathVariable Long id, @RequestBody SupportTicketRequest dto) {
        return service.update(id, dto);
    }

    @Operation(summary = "Delete a support ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Support ticket deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Support ticket not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
