package com.finpulse.api.repository;

import com.finpulse.api.entity.Category;
import com.finpulse.api.entity.Transaction;
import com.finpulse.api.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    List<Transaction> findByUserAndTransactionDateBetween(User user, LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(sum(t.amount),0) FROM Transaction t Where t.user = :user AND t.type = :type")
    BigDecimal sumByUserAndType(@Param("user")User user,@Param("type") Transaction.TransactionType type);

    @Query("SELECT COALESCE(sum(t.amount),0) FROM Transaction t Where t.user = :user AND t.type = :type AND MONTH(t.transactionDate) = MONTH(CURRENT_DATE) AND YEAR(t.transactionDate) = YEAR(CURRENT_DATE)")
    BigDecimal sumByUserAndTypeThisMonth(@Param("user")User user,@Param("type") Transaction.TransactionType type);

    // Sum by user, type, and a specified month
    @Query("SELECT COALESCE(sum(t.amount),0) FROM Transaction t WHERE t.user = :user AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserAndTypeAndMonth(@Param("user") User user, @Param("type") Transaction.TransactionType type, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    // Total spent per category for a user (all time)
    @Query("SELECT t.category, COALESCE(sum(t.amount),0) FROM Transaction t WHERE t.user = :user GROUP BY t.category")
    List<Object[]> sumByUserGroupByCategory(@Param("user") User user);

    // Total spent per category within a specified month
    @Query("SELECT t.category, COALESCE(sum(t.amount),0) FROM Transaction t WHERE t.user = :user AND t.transactionDate BETWEEN :startDate AND :endDate GROUP BY t.category")
    List<Object[]> sumByUserGroupByCategoryAndMonth(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Total spent per category for current month
    @Query("SELECT t.category, COALESCE(sum(t.amount),0) FROM Transaction t WHERE t.user = :user AND t.transactionDate BETWEEN :startDate AND :endDate GROUP BY t.category")
    List<Object[]> sumByUserGroupByCategoryCurrentMonth(@Param("user") User user, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Recent transactions with a limit
    List<Transaction> findByUserOrderByTransactionDateDesc(User user, Pageable pageable);


    List<Transaction> findByCategory(Category category);




}