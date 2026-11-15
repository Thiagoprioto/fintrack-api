package com.thiago.fintrack.service;

import com.thiago.fintrack.dto.request.CategoryRequest;
import com.thiago.fintrack.dto.response.CategoryResponse;
import com.thiago.fintrack.entity.Category;
import com.thiago.fintrack.entity.User;
import com.thiago.fintrack.exception.AlreadyExistsException;
import com.thiago.fintrack.exception.NotFoundException;
import com.thiago.fintrack.repository.CategoryRepository;
import com.thiago.fintrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;


    @Transactional
    public CategoryResponse create(Long userId, CategoryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (categoryRepository.existsByUserIdAndName(userId, request.name())) {
            throw new AlreadyExistsException("Categoria já cadastrada");
        }

        Category category = new Category();
        category.setName(request.name());
        category.setUser(user);

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll(Long userId) {
        return categoryRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse update(Long userId, Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        if (!category.getName().equals(request.name())
                && categoryRepository.existsByUserIdAndName(userId, request.name())) {
            throw new AlreadyExistsException("Categoria já cadastrada");
        }

        category.setName(request.name());

        return toResponse(category);
    }

    @Transactional
    public void delete(Long userId, Long categoryId){
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        categoryRepository.delete(category);
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
