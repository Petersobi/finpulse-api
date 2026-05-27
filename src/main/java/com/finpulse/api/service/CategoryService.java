package com.finpulse.api.service;

import com.finpulse.api.dto.CategoryRequest;
import com.finpulse.api.dto.CategoryResponse;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public CategoryResponse createCategory(CategoryRequest request, User user){
        Category category = Category.builder()
                .name(request.getName())
                .type(request.getType())
                .user(user)
                .build();
        Category saved = categoryRepository.save(category);
        return mapToResponse(saved);
    }

    public CategoryResponse getUserCategory(Long id,User user){
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Category not found")
        );
        if (!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized");
        }
        return mapToResponse(category);
    }

    public List<CategoryResponse> getUserCategories(User user){
        return  categoryRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public CategoryResponse updateCategory(Long id,CategoryRequest request,User user){
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Category not found")
        );
        if(!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized");
        }
        category.setName(request.getName());
        category.setType(request.getType());
        categoryRepository.save(category);
        return mapToResponse(category);
    }
    public void deleteCategory(Long id,User user){
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Category not found")) ;
        if(!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized");
        }
        List<Transaction> transactions = transactionRepository.findByCategory(category);
        if (!transactions.isEmpty()) {
            throw new RuntimeException(
                    "Cannot delete category with existing transactions. " +
                            "Please reassign or delete the transactions first."
            );
        }

        categoryRepository.delete(category);
    }

    private CategoryResponse mapToResponse(Category category){
        return  CategoryResponse.builder().id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
    }
}
