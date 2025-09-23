package dev.eliezer.superticket.modules.user.controller;

import dev.eliezer.superticket.modules.user.entities.User;
import dev.eliezer.superticket.dto.UserForUpdateRequestDTO;
import dev.eliezer.superticket.modules.user.dtos.UserResponseDTO;
import dev.eliezer.superticket.modules.user.useCases.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "RESTful API for managing users.")
public record UserRestController (FindUserUseCase findUserUseCase, FindUserByIdUseCase findUserByIdUseCase,
                                  CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase,
                                  DeleteUserUseCase deleteUserUseCase){

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")//annotation for Swagger
    @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Iterable<UserResponseDTO>> index(@RequestParam(value="search", defaultValue="") String search,
                                                           @RequestParam(value="type", defaultValue="") Long typeSearch ){

        var allUsers = findUserUseCase.execute(search, typeSearch);
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
        var user = findUserByIdUseCase.execute(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user's data")
    @ApiResponse(responseCode = "201", description = "User created successfully",  content = {
                    @Content(schema = @Schema(implementation = UserResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid user data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    //@SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody User userToInsert, HttpServletRequest request){
//        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
//        if (userRole != 2) {
//            throw new BusinessException("Unauthorized Access.");
//        }
        var userInserted = createUserUseCase.execute(userToInsert);
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
    public ResponseEntity<Object> update(@Valid HttpServletRequest request, @RequestBody UserForUpdateRequestDTO userUpdate, @PathVariable Long id){
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        Long tokenUserId = Long.valueOf(request.getAttribute("user_id").toString());
        if (userRole == 3 || (userRole == 1 && !id.equals(tokenUserId))) {
            throw new BusinessException("Unauthorized Access.");
        }


        try {
            var userUpdated = updateUserUseCase.execute(id, userUpdate, userRole);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userUpdated.getId())
                    .toUri();
            return ResponseEntity.created(location).body(userUpdated);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Delete an existing user based on its ID")
    @ApiResponse(responseCode = "200", description = "User successfully deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request){
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole != 2) {
            throw new BusinessException("Unauthorized Access.");
        }
        deleteUserUseCase.execute(id);
        return ResponseEntity.ok("user successfully deleted");
    }





}
