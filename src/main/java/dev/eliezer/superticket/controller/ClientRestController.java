package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/clients")
@Tag(name = "Clients", description = "RESTful API for managing clients.")
@SecurityRequirement(name = "jwt_auth")
public record ClientRestController(ClientService clientService) {
    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieve a list of all registered clients")
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = Client.class)))
    }  )
    public ResponseEntity<Object> index(@RequestParam(value="search", defaultValue="") String search,
                                        @RequestParam(value="type", defaultValue="") Long type ) {
        var client = clientService.index(search, type);
        return ResponseEntity.ok(client);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by ID", description = "Retrieve a specific client based on its ID")
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Client> findById(@PathVariable Long id){
        var client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    @Operation(summary = "Create a new client", description = "Create a new client and return the created client's data")
    @ApiResponse(responseCode = "201", description = "Client created successfully", content = {
            @Content(schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "422", description = "Invalid client data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Client> insert(@Valid @RequestBody Client clientToInsert){
        var clientInserted = clientService.insert(clientToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(clientInserted);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a client", description = "Update the data of an existing client based on its ID")
    @ApiResponse(responseCode = "200", description = "Client updated successfully", content = {
            @Content(schema = @Schema(implementation = Client.class))})
    @ApiResponse(responseCode = "404", description = "Client not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @ApiResponse(responseCode = "422", description = "Invalid client data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Client> update(@Valid @PathVariable Long id, @RequestBody Client clientToUpdate){
        var clientUpdated = clientService.update(id, clientToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(clientUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client", description = "Delete an existing client based on its ID")
    @ApiResponse(responseCode = "200", description = "Client successfully deleted")
    @ApiResponse(responseCode = "404", description = "Client not found")

    public ResponseEntity<String> delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.ok("client successfully deleted");
    }


}
