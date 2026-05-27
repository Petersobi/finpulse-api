package com.finpulse.api.service;

import com.finpulse.api.dto.BudgetRequest;
import com.finpulse.api.dto.BudgetResponse;
import com.finpulse.api.entity.Budget;
import com.finpulse.api.entity.Category;
import com.finpulse.api.entity.User;
import com.finpulse.api.repository.BudgetRepository;
import com.finpulse.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetResponse saveBudget(BudgetRequest request, User user){
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found"));

        if (budgetRepository.existsByUserIdAndCategoryIdAndBudgetMonth(user.getId(), request.getCategoryId(), request.getBudgetMonth())){
        throw new DuplicateBudgetException(
                "A budget already exists for this category and month.");
    }
    Budget budget = Budget.builder()
            .amount(request.getAmount())
            .budgetMonth(request.getBudgetMonth())
            .category(category)
            .user(user)
            .build();
        return mapToResponse(budgetRepository.save(budget));
    }
    public BudgetResponse getBudget(Long id,User user){
        Budget budget = budgetRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Budget not found.")
        );
        if(!budget.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized");
        }
        return mapToResponse(budget);
    }
    public List<BudgetResponse> getBudgets(User user){
        return  budgetRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public BudgetResponse updateBudget(Long id,BudgetRequest request,User user){
        Budget budget = budgetRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Budget not found.")
        );
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new RuntimeException("Category not found"));

        if(!budget.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized");
        }
        budget.setBudgetMonth(request.getBudgetMonth());
        budget.setAmount(request.getAmount());
        budget.setCategory(category);
        return mapToResponse(budgetRepository.save(budget));
    }
    public void deleteBudget(Long id,User user){
        Budget budget = budgetRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Budget not found.")
        );
        if(!budget.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized");
        }
        budgetRepository.delete(budget);
    }
    private BudgetResponse mapToResponse(Budget budget){
        return BudgetResponse.builder()
                .id(budget.getId())
                .categoryName(budget.getCategory().getName())
                .yearMonth(budget.getBudgetMonth())
                .amount(budget.getAmount())
                .build();
    }
}
