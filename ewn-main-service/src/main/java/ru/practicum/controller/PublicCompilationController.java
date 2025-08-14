package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                                @RequestParam(defaultValue = "0") Integer from,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        List<CompilationDto> compilations = compilationService.getAllCompilation(pinned, from, size);
        log.info("Подборка событий предоставлена");
        return ResponseEntity.ok(compilations);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable Integer compId) {
        CompilationDto compilations = compilationService.getCompilationDtoById(compId);
        log.info("Подборка событий  по Id=" + compId + " предоставлена");
        return ResponseEntity.ok(compilations);
    }
}