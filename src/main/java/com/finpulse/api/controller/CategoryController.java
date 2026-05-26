package com.finpulse.api.controller;

import com.finpulse.api.dto.CategoryRequest;
import com.finpulse.api.dto.CategoryResponse;
import com.finpulse.api.entity.User;
import com.finpulse.api.service.CategoryService;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(categoryService.createCategory(request,user));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(categoryService.getUserCategory(id,user));
    }
    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(categoryService.getUserCategories(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(categoryService.updateCategory(id,request,user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Valid> deleteCategory(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        categoryService.deleteCategory(id,user);
        return ResponseEntity.noContent().build();
    }

}
