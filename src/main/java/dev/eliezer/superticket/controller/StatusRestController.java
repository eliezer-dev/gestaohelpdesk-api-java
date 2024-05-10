package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.service.StatusService;
import dev.eliezer.superticket.service.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/status")
@Tag(name = "Status", description = "RESTful API for managing status registration.")
@SecurityRequirement(name = "jwt_auth")
public record StatusRestController(StatusService statusService) {
    @GetMapping
    @Operation(summary = "Get all status", description = "Retrieve a list of all registered status")//annotation for Swagger
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = Status.class)))
    }  )
    public ResponseEntity<Iterable<Status>> findAll(){
        var status = statusService.findAll();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a status by ID", description = "Retrieve a specific status based on its ID")
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(schema = @Schema(implementation = Status.class))})
    @ApiResponse(responseCode = "404", description = "Status not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Status> findById(@PathVariable Long id){
        var status = statusService.findById(id);
        return ResponseEntity.ok(status);
    }

    @PostMapping
    @Operation(summary = "Create a new status", description = "Create a new status and return the created status data")
    @ApiResponse(responseCode = "201", description = "Status created successfully", content = {
            @Content(schema = @Schema(implementation = Status.class))})
    @ApiResponse(responseCode = "422", description = "Invalid status data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Status> insert(@Valid @RequestBody Status statusToInsert, HttpServletRequest request) {
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole != 2) {
            throw new BusinessException("Unauthorized Access.");
        }
        var statusInserted = statusService.insert(statusToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(statusInserted.getId())
                 .toUri();
        return ResponseEntity.created(location).body(statusInserted);

        }

    @PutMapping("/{id}")
    @Operation(summary = "Update a status", description = "Update the data of an existing status based on its ID")
    @ApiResponse(responseCode = "200", description = "Status updated successfully", content = {
            @Content(schema = @Schema(implementation = Status.class))})
    @ApiResponse(responseCode = "404", description = "Status not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @ApiResponse(responseCode = "422", description = "Invalid status data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Status> update(@PathVariable Long id, @RequestBody Status statusToUpdate, HttpServletRequest request){
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole != 2) {
            throw new BusinessException("Unauthorized Access.");
        }
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
            @ApiResponse(responseCode = "200", description = "Status successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Status not found")
    })
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request){
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole != 2) {
            throw new BusinessException("Unauthorized Access.");
        }
        statusService.delete(id);
        return ResponseEntity.ok("status successfully deleted");
    }
    
}
