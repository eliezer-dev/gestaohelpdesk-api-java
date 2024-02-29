package dev.eliezer.superticket.controller;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.dto.AuthUserRequestDTO;
import dev.eliezer.superticket.dto.AuthUserResponseDTO;
import dev.eliezer.superticket.dto.UserForUpdateRequestDTO;
import dev.eliezer.superticket.dto.UserResponseDTO;
import dev.eliezer.superticket.service.UserService;
import dev.eliezer.superticket.service.impl.AuthUserServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "RESTful API for managing users.")
public record UserRestController (UserService userService, AuthUserServiceImpl authUserServiceImpl){

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")//annotation for Swagger
    @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))
    })

    public ResponseEntity<Iterable<UserResponseDTO>> findAll(){
        var allUsers = userService.findAll();

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID", description = "Retrieve a specific user based on its ID")
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(schema = @Schema(implementation = UserResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "User not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        var user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user's data")
    @ApiResponse(responseCode = "201", description = "User created successfully",  content = {
                    @Content(schema = @Schema(implementation = UserResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid user data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody User userToInsert){
        var userInserted = userService.insert(userToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(userInserted);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Update the data of an existing user based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = {
                    @Content(schema = @Schema(implementation = UserResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = {
                    @Content(schema = @Schema(implementation = Object.class))}),
            @ApiResponse(responseCode = "422", description = "Invalid user data provided", content = {
                    @Content(schema = @Schema(implementation = Object.class))})
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserResponseDTO> update(@Valid HttpServletRequest request, @RequestBody UserForUpdateRequestDTO userUpdate){
        Long id = Long.valueOf(request.getAttribute("user_id").toString());
        var userUpdated = userService.update(id, userUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(userUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
    @ApiResponse(responseCode = "200", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("user successfully deleted");
    }

    @PostMapping("/auth")
    @Operation(summary = "Authenticate a user", description = "Authenticate a user and generate a token")
    @ApiResponse(responseCode = "200", description = "Token generated successfully", content = {
            @Content(schema = @Schema(implementation = AuthUserResponseDTO.class))})
    @ApiResponse(responseCode = "401", description = "Unauthorized user")
    @Tag(name = "Auth", description = "RESTful API for managing users.")
    public ResponseEntity<Object>auth(@RequestBody AuthUserRequestDTO authUserRequestDTO){
        try {
            var token = this.authUserServiceImpl.execute(authUserRequestDTO);
            return ResponseEntity.ok().body(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }



}
