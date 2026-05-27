package com.finpulse.api.repository;

import com.finpulse.api.entity.Budget;
import com.finpulse.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
    boolean existsByUserIdAndCategoryIdAndBudgetMonth(
            Long userId, Long categoryId, YearMonth budgetMonth
    );
    List<Budget> findByUser(User user);
}
