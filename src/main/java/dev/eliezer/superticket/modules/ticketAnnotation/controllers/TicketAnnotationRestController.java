package dev.eliezer.superticket.modules.ticketAnnotation.controllers;

import dev.eliezer.superticket.modules.ticketAnnotation.entities.TicketAnnotation;
import dev.eliezer.superticket.dto.TicketAnnotationRequestDTO;
import dev.eliezer.superticket.dto.TicketAnnotationResponseDTO;
import dev.eliezer.superticket.modules.ticketAnnotation.useCases.DeleteTicketAnnotationUseCase;
import dev.eliezer.superticket.modules.ticketAnnotation.useCases.FindTicketAnnotationByIdUseCase;
import dev.eliezer.superticket.modules.ticketAnnotation.useCases.FindTicketAnnotationByTicketIdUseCase;
import dev.eliezer.superticket.modules.ticketAnnotation.useCases.InsertTicketAnnotationUseCase;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tickets/annotations")
@Tag(name = "Annotations of Tickets", description = "RESTful API for managing annotation of tickets.")
@SecurityRequirement(name = "jwt_auth")
public record TicketAnnotationRestController(FindTicketAnnotationByTicketIdUseCase findTicketAnnotationByTicketIdUseCase,
                                             FindTicketAnnotationByIdUseCase findTicketAnnotationByIdUseCase,
                                             InsertTicketAnnotationUseCase insertTicketAnnotationUseCase,
                                             DeleteTicketAnnotationUseCase deleteTicketAnnotationUseCase) {

    @GetMapping
    @Operation(summary = "Get all ticket annotations by ticket ID", description = "Retrieve a list of all registered annotations of ticket")//annotation for Swagger
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = TicketAnnotationRequestDTO.class)))})
    public ResponseEntity<List<TicketAnnotationResponseDTO>> index(@RequestParam(value="ticket") Long ticketId){
        var ticketAnnotations = findTicketAnnotationByTicketIdUseCase.execute(ticketId);
        return ResponseEntity.ok(ticketAnnotations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket annotation by ID", description = "Retrieve a specific ticket annotation based on its ID")//annotation for Swagger
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = TicketAnnotationRequestDTO.class)))})
    public ResponseEntity<TicketAnnotationResponseDTO> findById(@PathVariable Long id){
        var ticketAnnotation = findTicketAnnotationByIdUseCase.execute(id);
        return ResponseEntity.ok(ticketAnnotation);
    }


    @PostMapping
    @Operation(summary = "Create a new annotation", description = "Insert into ticket a new annotation and return the created annotation's data")
    @ApiResponse(responseCode = "201", description = "Annotation inserted successfully", content = {
            @Content(schema = @Schema(implementation = TicketAnnotation.class))})
    @ApiResponse(responseCode = "422", description = "Invalid annotation data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<TicketAnnotationResponseDTO> insert(@Valid @RequestBody TicketAnnotationRequestDTO request, HttpServletRequest httpRequest){
        Long userRole = Long.valueOf(httpRequest.getAttribute("user_role").toString());
        if (userRole == 3) {
            throw new BusinessException("Unauthorized Access.");
        }
        var annotationInserted = insertTicketAnnotationUseCase.execute(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(annotationInserted.getId())
                .toUri();

        return ResponseEntity.created(location).body(annotationInserted);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a ticket annotation", description = "Delete an existing ticket annotation based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket annotation successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Ticket annotation not found")
    })
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request){
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole == 3) {
            throw new BusinessException("Unauthorized Access.");
        }
        deleteTicketAnnotationUseCase.execute(id);
        return ResponseEntity.ok("ticket annotation successfully deleted");
    }
}
