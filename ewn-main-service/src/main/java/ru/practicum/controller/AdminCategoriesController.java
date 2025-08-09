package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;
import ru.practicum.service.CategoriesService;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class AdminCategoriesController {

    private final CategoriesService categoriesService;

    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        CategoryDto categoryDto = categoriesService.saveCategory(newCategoryDto);
        log.info("Категория добавлена");
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody NewCategoryDto newCategoryDto,
                                                      @PathVariable Integer catId) {
        CategoryDto categoryDto = categoriesService.updateCategory(newCategoryDto, catId);
        log.info("Категория обновлена");
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer catId) {
        String text = categoriesService.deleteCategory(catId);
        log.info("Категория удалена");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(text);
    }
}
