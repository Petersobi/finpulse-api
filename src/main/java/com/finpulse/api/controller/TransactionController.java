package com.finpulse.api.controller;

import com.finpulse.api.dto.TransactionRequest;
import com.finpulse.api.dto.TransactionResponse;
import com.finpulse.api.entity.User;
import com.finpulse.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.createTransaction(request, user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
       return ResponseEntity.ok(transactionService.getUserTransaction(id, user));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(transactionService.getUserTransactions(user));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request,
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(transactionService.updateTransaction(id,request,user));
    }
    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal User user){
        transactionService.deleteTransaction(id,user);
        return ResponseEntity.noContent().build();
    }
}
