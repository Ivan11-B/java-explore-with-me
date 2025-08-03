package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class PublicCategoriesController {

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam Integer from,
                                                          @RequestParam Integer size) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/catId")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer catId) {
        return ResponseEntity.ok(null);
    }
}
