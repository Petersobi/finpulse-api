package com.finpulse.api.controller;

import com.finpulse.api.dto.CategoryRequest;
import com.finpulse.api.dto.CategoryResponse;
import com.finpulse.api.entity.User;
import com.finpulse.api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "Manage your categories")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create a new category")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(categoryService.createCategory(request,user));
    }
    @Operation(summary = "Get a single category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(categoryService.getUserCategory(id,user));
    }
    @Operation(summary = "Get all categories")
    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(categoryService.getUserCategories(user));
    }

    @Operation(summary = "Update a category")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(categoryService.updateCategory(id,request,user));
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping("/{id}")
    public ResponseEntity<Valid> deleteCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        categoryService.deleteCategory(id,user);
        return ResponseEntity.noContent().build();
    }

}
