package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.dto.NewCategoryDto;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody NewCategoryDto newCategoryDto) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody NewCategoryDto newCategoryDto,
                                                      @PathVariable Integer catId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer catId) {
        return ResponseEntity.ok("Категория удалена");
    }
}
