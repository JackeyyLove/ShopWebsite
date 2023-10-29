package com.example.shopapp.service;

import com.example.shopapp.dto.CategoryDto;
import com.example.shopapp.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(long categoryId, CategoryDto categoryDto);
    void deleteCategory(long id);
}
