package com.finpulse.api.controller;

import com.finpulse.api.dto.CategoryRequest;
import com.finpulse.api.dto.CategoryResponse;
import com.finpulse.api.entity.User;
import com.finpulse.api.service.CategoryService;
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
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(categoryService.getUserCategories(user));
    }
}
