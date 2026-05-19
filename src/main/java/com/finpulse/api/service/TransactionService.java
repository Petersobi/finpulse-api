package com.finpulse.api.service;

import com.finpulse.api.dto.TransactionRequest;
import com.finpulse.api.dto.TransactionResponse;
import com.finpulse.api.entity.Category;
import com.finpulse.api.entity.Transaction;
import com.finpulse.api.entity.User;
import com.finpulse.api.repository.CategoryRepository;
import com.finpulse.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionResponse createTransaction(TransactionRequest request, User user){
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found"));

        Transaction transaction = Transaction.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .type(request.getType())
                .transactionDate(request.getTransactionDate())
                .category(category)
                .user(user)
                .build();
        Transaction saved = transactionRepository.save(transaction);
        return mapToResponse(saved);

    }
    public List<TransactionResponse> getUserTransactions(User user){
        return transactionRepository.findByUserOrderByTransactionDateDesc(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    private TransactionResponse mapToResponse(Transaction transaction){
        return TransactionResponse.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .transactionDate(transaction.getTransactionDate())
                .categoryName(transaction.getCategory().getName())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
