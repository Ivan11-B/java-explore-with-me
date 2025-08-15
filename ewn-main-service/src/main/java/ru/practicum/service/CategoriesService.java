package ru.practicum.service;

import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.model.Category;

import java.util.List;

public interface CategoriesService {

    CategoryDto saveCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(NewCategoryDto newCategoryDto, Integer catId);

    void deleteCategory(Integer catId);

    Category getCategoryById(Integer category);

    CategoryDto getCategoryDtoById(Integer catId);

    List<CategoryDto> getAllCategories(Integer from, Integer size);
}
