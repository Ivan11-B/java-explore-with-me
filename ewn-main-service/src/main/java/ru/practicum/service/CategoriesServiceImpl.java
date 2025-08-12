package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.exception.CategoryDuplicateException;
import ru.practicum.exception.DeleteCategoryException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.repository.CategoriesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final CategoryMapper categoryMapper;
    private final ControlReferenceEventsService controlReferenceEventsService;

    @Override
    @Transactional
    public CategoryDto saveCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryMapper.toEntity(newCategoryDto);
        try {
            Category savedCategory = categoriesRepository.save(category);
            return categoryMapper.toDto(savedCategory);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryDuplicateException("Данное название категории уже существует");
        }
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(NewCategoryDto newCategoryDto, Integer catId) {
        isExistsCategory(catId);
        if (categoriesRepository.existsByNameAndIdNot(newCategoryDto.getName(), catId)) {
            throw new CategoryDuplicateException("Данное название категории уже существует");
        }
        Category category = categoryMapper.toEntity(newCategoryDto);
        category.setId(catId);
        Category updatedCategory = categoriesRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer catId) {
        isExistsCategory(catId);
        if (!controlReferenceEventsService.getEventByCategory(catId)) {
            categoriesRepository.deleteById(catId);
        } else {
            throw new DeleteCategoryException("Данная категории имеет привязанные события");
        }
    }

    @Override
    public CategoryDto getCategoryDtoById(Integer catId) {
        return categoryMapper.toDto(getCategoryById(catId));
    }

    @Override
    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        List<Category> categories = categoriesRepository.findAllCategories(from, size);
        return categoryMapper.toDtoList(categories);
    }

    @Override
    public Category getCategoryById(Integer catId) {
        return categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id=" + catId + " не найдена"));
    }

    private void isExistsCategory(Integer catId) {
        if (!categoriesRepository.existsById(catId)) {
            throw new NotFoundException("Категория с id=" + catId + " не найдена");
        }
    }
}