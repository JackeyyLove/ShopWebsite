package com.example.shopapp.service;

import com.example.shopapp.dto.CategoryDto;
import com.example.shopapp.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(long categoryId, CategoryDto categoryDto);
    void deleteCategory(long id);
}
