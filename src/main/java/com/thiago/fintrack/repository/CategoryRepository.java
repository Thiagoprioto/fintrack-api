package com.thiago.fintrack.repository;

import com.thiago.fintrack.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long userId);

    boolean existsByUserIdAndName(Long userId, String name);

    Optional<Category> findByIdAndUserId(Long id, Long userId);
}
