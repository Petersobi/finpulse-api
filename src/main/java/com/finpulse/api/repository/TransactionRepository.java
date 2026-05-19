package com.finpulse.api.repository;

import com.finpulse.api.entity.Transaction;
import com.finpulse.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
}