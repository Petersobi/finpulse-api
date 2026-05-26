package com.finpulse.api.repository;

import com.finpulse.api.entity.Transaction;
import com.finpulse.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    @Query("SELECT COALESCE(sum(t.amount),0) FROM Transaction t Where t.user = :user AND t.type = :type")
    BigDecimal sumByUserAndType(@Param("user")User user,@Param("type") Transaction.TransactionType type);

    @Query("SELECT COALESCE(sum(t.amount),0) FROM Transaction t Where t.user = :user AND t.type = :type AND MONTH(t.transactionDate) = MONTH(CURRENT_DATE) AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE)")
    BigDecimal sumByUserAndTypeThisMonth(@Param("user")User user,@Param("type") Transaction.TransactionType type);




}