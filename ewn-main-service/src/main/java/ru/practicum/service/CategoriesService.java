package ru.practicum.service;

import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;

import java.util.List;

public interface CategoriesService {

    CategoryDto saveCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(NewCategoryDto newCategoryDto, Integer catId);

    String deleteCategory(Integer catId);

    CategoryDto getCategoryById(Integer catId);

    List<CategoryDto> getCategories(Integer from, Integer size);
}
