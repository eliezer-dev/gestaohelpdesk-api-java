package dev.eliezer.gestaohelpdesk.modules.auth.controllers;

import dev.eliezer.gestaohelpdesk.modules.auth.dtos.AuthUserRequestDTO;
import dev.eliezer.gestaohelpdesk.modules.auth.dtos.AuthUserResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.auth.useCases.AuthUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "RESTful API for authenticated users.")
public record AuthRestController(AuthUserUseCase authUserUseCase) {

    @PostMapping
    @Operation(summary = "Authenticate a user",
            description = "Authenticate a user and generate a token")
    @ApiResponse(responseCode = "200", description = "Token generated successfully", content = {
            @Content(schema = @Schema(implementation = AuthUserResponseDTO.class))})
    @ApiResponse(responseCode = "401", description = "Unauthorized user")
    @Tag(name = "Auth", description = "RESTful API for managing users.")
    public ResponseEntity<Object> auth(@RequestBody AuthUserRequestDTO authUserRequestDTO){
        try {
            var token = this.authUserUseCase.execute(authUserRequestDTO);
            return ResponseEntity.ok().body(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
