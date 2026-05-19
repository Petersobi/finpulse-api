package com.finpulse.api.dto;

import com.finpulse.api.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {

    @NotBlank (message = "Description is required")
    private String description;

    @NotNull (message = "Amount is required")
    @Positive (message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull (message = "Transaction type is required")
    private Transaction.TransactionType type;

    @NotNull (message = "Transaction date is required")
    private LocalDateTime transactionDate;

    @NotNull (message = "Category is required")
    private Long categoryId;
}
