package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;

@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {

    @PostMapping
    public ResponseEntity<CompilationDto> saveCompilation(@RequestBody NewCompilationDto newCompilationDto) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<String> deleteCompilation(@PathVariable Integer compId) {
        return ResponseEntity.ok("Подборка удалена");
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable Integer compId,
                                                            @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return ResponseEntity.ok(null);
    }
}
