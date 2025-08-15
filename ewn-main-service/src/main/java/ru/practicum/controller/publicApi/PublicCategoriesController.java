package ru.practicum.controller.publicApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.service.CategoriesService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Slf4j
@RequiredArgsConstructor
public class PublicCategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "0") Integer from,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        List<CategoryDto> categories = categoriesService.getAllCategories(from, size);
        log.info("Список категорий получен");
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer catId) {
        CategoryDto categoryDto = categoriesService.getCategoryDtoById(catId);
        log.info("Категория получена");
        return ResponseEntity.ok(categoryDto);
    }
}
