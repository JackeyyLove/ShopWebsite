package com.example.shopapp.mapping;

import com.example.shopapp.dto.CategoryDto;
import com.example.shopapp.model.Category;

public class CategoryMapper {
    public static Category mapToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(),categoryDto.getName());
    }
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

}
