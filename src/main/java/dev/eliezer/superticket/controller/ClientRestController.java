package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Client;
import dev.eliezer.superticket.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clients")
@Tag(name = "Clients Controller", description = "RESTful API for managing clients.")
public record ClientRestController(ClientService clientService) {
    @GetMapping
    @Operation(summary = "Get all clients", description = "Retrieve a list of all registered clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<Iterable<Client>> findAll(){
        var client = clientService.findAll();
        return ResponseEntity.ok(client);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by ID", description = "Retrieve a specific client based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Client> findById(@PathVariable Long id){
        var client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    @Operation(summary = "Create a new client", description = "Create a new client and return the created client's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid client data provided")
    })
    public ResponseEntity<Client> insert(@RequestBody Client clientToInsert){
        var clientInserted = clientService.insert(clientToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(clientInserted);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a client", description = "Update the data of an existing client based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "422", description = "Invalid client data provided")
    })
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client clientToUpdate){
        var clientUpdated = clientService.update(id, clientToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(clientUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client", description = "Delete an existing client based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
