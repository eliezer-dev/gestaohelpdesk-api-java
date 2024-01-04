package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Ticket;
import dev.eliezer.superticket.dto.TicketRequestDTO;
import dev.eliezer.superticket.dto.TicketResponseDTO;
import dev.eliezer.superticket.dto.UserResponseDTO;
import dev.eliezer.superticket.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin //cors
@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets", description = "RESTful API for managing tickets.")
@SecurityRequirement(name = "jwt_auth")
public record TicketRestController(TicketService ticketService) {
    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieve a list of all registered tickets")//annotation for Swagger
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = TicketResponseDTO.class)))})
    public ResponseEntity<Iterable<TicketResponseDTO>> findAll(){
        var ticket = ticketService.findAll();
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a ticket by ID", description = "Retrieve a specific ticket based on its ID")
    @ApiResponse(responseCode = "200", description = "Operation successful",content = {
            @Content(schema = @Schema(implementation = TicketResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "Ticket not found", content = {
                @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable Long id){
        var ticket = ticketService.findById(id);
        return ResponseEntity.ok(ticket);
    }
    @PostMapping
    @Operation(summary = "Create a new ticket", description = "Create a new ticket and return the created ticket's data")
    @ApiResponse(responseCode = "201", description = "Ticket created successfully", content = {
                @Content(schema = @Schema(implementation = TicketResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid ticket data provided", content = {
                @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<TicketResponseDTO> insert(@Valid @RequestBody TicketRequestDTO ticketToInsert){
        var ticketInserted = ticketService.insert(ticketToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticketInserted.getId())
                .toUri();

        return ResponseEntity.created(location).body(ticketInserted);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a ticket", description = "Update the data of an existing ticket based on its ID")
    @ApiResponse(responseCode = "200", description = "Ticket updated successfully", content = {
                @Content(schema = @Schema(implementation = TicketResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "Ticket not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @ApiResponse(responseCode = "422", description = "Invalid ticket data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<TicketResponseDTO> update(@Valid @PathVariable Long id, @RequestBody TicketRequestDTO ticketToUpdate){
        var ticketUpdated = ticketService.update(id, ticketToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticketUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(ticketUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket", description = "Delete an existing ticket based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public ResponseEntity<String> delete(@PathVariable Long id){
        ticketService.delete(id);
        return ResponseEntity.ok("ticket successfully deleted");
    }

}

