package com.finpulse.api.controller;

import com.finpulse.api.dto.BudgetRequest;
import com.finpulse.api.dto.BudgetResponse;
import com.finpulse.api.entity.User;
import com.finpulse.api.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Budgets", description = "Manage your budgets")
@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;
    @Operation(summary = "Create a new budget")
    @PostMapping
    public ResponseEntity<BudgetResponse> saveBudget(
            @Valid @RequestBody BudgetRequest request,
            @AuthenticationPrincipal User user
            ){
        return ResponseEntity.ok(budgetService.saveBudget(request,user));
    }
    @Operation(summary = "Get a single budget by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudget(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(budgetService.getBudget(id,user));
    }
    @Operation(summary = "Get all budget")
    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getBudgets(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(budgetService.getBudgets(user));
    }
    @Operation(summary = "Update a budget")
    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest request,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(budgetService.updateBudget(id,request,user));
    }

    @Operation(summary = "Delete a budget")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){ budgetService.deleteBudget(id,user);
        return ResponseEntity.noContent().build();
    }
}
