package dev.eliezer.superticket.modules.category.controllers;

import dev.eliezer.superticket.modules.category.entities.Category;
import dev.eliezer.superticket.modules.category.useCases.*;
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

@CrossOrigin
@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "RESTful API for managing categories.")
@SecurityRequirement(name = "jwt_auth")
public record CategoryRestController (CreateCategoryUseCase createCategoryUseCase,
                                      DeleteCategoryUseCase deleteCategoryUseCase,
                                      FindCategoriesUseCase findCategoriesUseCase,
                                      FindCategoryByIdUseCase findCategoryByIdUseCase,
                                      UpdateCategoryUseCase updateCategoryUseCase) {

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve a list of all registered categories")
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class)))
    }  )
    public ResponseEntity<Iterable<Category>> index() {
        var category = findCategoriesUseCase.execute();
        return ResponseEntity.ok(category);

    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by ID", description = "Retrieve a specific category based on its ID")
    @ApiResponse(responseCode = "200", description = "Operation successful", content = {
            @Content(schema = @Schema(implementation = Category.class))})
    @ApiResponse(responseCode = "404", description = "Category not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Category> findById(@PathVariable Long id){
        var category = findCategoryByIdUseCase.execute(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @Operation(summary = "Create a new category", description = "Create a new category and return the created category data")
    @ApiResponse(responseCode = "201", description = "Category created successfully", content = {
            @Content(schema = @Schema(implementation = Category.class))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Category> insert(@Valid @RequestBody Category categoryToInsert, HttpServletRequest request) {
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole != 2) {
            throw new BusinessException("Unauthorized Access.");
        }
        var categoryInserted = createCategoryUseCase.execute(categoryToInsert);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryInserted.getId())
                .toUri();
        return ResponseEntity.created(location).body(categoryInserted);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a status", description = "Update the data of an existing category based on its ID")
    @ApiResponse(responseCode = "200", description = "Category updated successfully", content = {
            @Content(schema = @Schema(implementation = Category.class))})
    @ApiResponse(responseCode = "404", description = "Category not found", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category categoryToUpdate, HttpServletRequest request){
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole != 2) {
            throw new BusinessException("Unauthorized Access.");
        }
        var categoryUpdated = updateCategoryUseCase.execute(id, categoryToUpdate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryUpdated.getId())
                .toUri();
        return ResponseEntity.created(location).body(categoryUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Delete an existing category based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request){
        Long userRole = Long.valueOf(request.getAttribute("user_role").toString());
        if (userRole != 2) {
            throw new BusinessException("Unauthorized Access.");
        }
        deleteCategoryUseCase.execute(id);
        return ResponseEntity.ok("category successfully deleted");
    }



}
