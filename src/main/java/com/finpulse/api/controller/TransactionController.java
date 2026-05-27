package com.finpulse.api.controller;

import com.finpulse.api.dto.TransactionRequest;
import com.finpulse.api.dto.TransactionResponse;
import com.finpulse.api.entity.User;
import com.finpulse.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Tag(name = "Transactions", description = "Manage your transactions")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Create a new transaction")
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.createTransaction(request, user));
    }

    @Operation(summary = "Get a single transaction by ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
       return ResponseEntity.ok(transactionService.getUserTransaction(id, user));
    }

    @Operation(summary = "Get all transactions")
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(transactionService.getUserTransactions(user));
    }
    @Operation(summary = "Update a transaction")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(transactionService.updateTransaction(id,request,user));
    }
    @Operation(summary = "Get transactions for specified month")
    @GetMapping("/monthly")
    public ResponseEntity<List<TransactionResponse>> getTransactionsForMonth(
            @RequestParam String yearMonth,
            @AuthenticationPrincipal User user){
        YearMonth ym = YearMonth.parse(yearMonth);
       return ResponseEntity.ok(transactionService.getUserTransactionsForMonth(ym,user));
    }

    @Operation(summary = "Get transactions for specified period")
    @GetMapping("/period")
    public ResponseEntity<List<TransactionResponse>> getTransactionsForPeriod(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @AuthenticationPrincipal User user
    ){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
       return ResponseEntity.ok(transactionService.getUserTransactionsForPeriod(start,end,user));
    }
    @Operation(summary = "Delete a transaction")
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        transactionService.deleteTransaction(id,user);
        return ResponseEntity.noContent().build();
    }
}
