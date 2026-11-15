package com.thiago.fintrack.controller;

import com.thiago.fintrack.dto.request.CategoryRequest;
import com.thiago.fintrack.dto.response.CategoryResponse;
import com.thiago.fintrack.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/home")
    public ResponseEntity<List<CategoryResponse>> listAllCategory(@RequestParam Long userId){
        List<CategoryResponse> category = categoryService.findAll(userId);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> createCategory(@RequestParam Long userId,
                                                           @RequestBody @Valid CategoryRequest request){
        CategoryResponse created = categoryService.create(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@RequestParam Long userId,
                                               @PathVariable Long categoryId){
        categoryService.delete(userId, categoryId);
        return ResponseEntity.noContent().build();
    }


}
