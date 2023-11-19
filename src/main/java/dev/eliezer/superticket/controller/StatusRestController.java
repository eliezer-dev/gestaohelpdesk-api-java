package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/status")
@Tag(name = "Status Controller", description = "RESTful API for managing status registration.") //annotation for Swagger
public record StatusRestController(StatusService statusService) {
    @GetMapping
    @Operation(summary = "Get all status", description = "Retrieve a list of all registered status")//annotation for Swagger
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<Iterable<Status>> findAll(){
        var status = statusService.findAll();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a status by ID", description = "Retrieve a specific status based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Status not found")
    })
    public ResponseEntity<Status> findById(@PathVariable Long id){
        var status = statusService.findById(id);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    @Operation(summary = "Create a new status", description = "Create a new status and return the created status data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Status created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid status data provided")
    })
    public ResponseEntity<Status> insert(@RequestBody Status statusToInsert){
        var statusInserted = statusService.insert(statusToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(statusInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(statusInserted);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a status", description = "Update the data of an existing status based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Status not found"),
            @ApiResponse(responseCode = "422", description = "Invalid status data provided")
    })
    public ResponseEntity<Status> update(@PathVariable Long id, @RequestBody Status statusToUpdate){
        var statusUpdated = statusService.update(id, statusToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(statusUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(statusUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a status", description = "Delete an existing status based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Status not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        statusService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
