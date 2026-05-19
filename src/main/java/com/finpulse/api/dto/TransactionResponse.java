package com.finpulse.api.dto;

import com.finpulse.api.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private Transaction.TransactionType type;
    private LocalDateTime transactionDate;
    private String categoryName;
    private LocalDateTime createdAt;
}
