package dev.eliezer.gestaohelpdesk.modules.system.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin //cors
@RestController
@RequestMapping("/system")
@Tag(name = "System", description = "RESTful API for verify system information.")
@SecurityRequirement(name = "jwt_auth")
public record SystemRestController() {

    @GetMapping("/status")
    @Operation(summary = "Get system status", description = "Return a status of system")//annotation for Swagger
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(examples = @ExampleObject("Gestão Helpdesk is working"))})
    public ResponseEntity<String> status(){
        return ResponseEntity.ok("Gestao Helpdesk is working in version 0.1.4");
    }



}
