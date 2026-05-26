package com.finpulse.api.service;

import com.finpulse.api.dto.DashboardResponse;
import com.finpulse.api.entity.Transaction;
import com.finpulse.api.entity.User;
import com.finpulse.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashBoardService {
    private final TransactionRepository transactionRepository;
    public DashboardResponse getDashBoard(User user){
        BigDecimal totalIncome = transactionRepository.sumByUserAndType(user, Transaction.TransactionType.INCOME);
        BigDecimal totalExpense = transactionRepository.sumByUserAndType(user, Transaction.TransactionType.EXPENSE);
        BigDecimal netBalance = totalIncome.subtract(totalExpense);
        BigDecimal monthlyIncome = transactionRepository.sumByUserAndTypeThisMonth(user, Transaction.TransactionType.INCOME);
        BigDecimal monthlyExpense = transactionRepository.sumByUserAndTypeThisMonth(user, Transaction.TransactionType.EXPENSE);
        BigDecimal monthlyBalance = monthlyIncome.subtract(monthlyExpense);
        long totalTransactions = transactionRepository.findByUser(user).size();

        return DashboardResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .netBalance(netBalance)
                .monthlyIncome(monthlyIncome)
                .monthlyExpense(monthlyExpense)
                .monthlyBalance(monthlyBalance)
                .totalTransactions(totalTransactions)
                .build();


    }
}
