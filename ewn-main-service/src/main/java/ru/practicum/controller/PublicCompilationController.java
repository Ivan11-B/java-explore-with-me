package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;

import java.util.List;

@RestController
@RequestMapping("/compilation")
public class PublicCompilationController {

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getCompilations(@RequestParam Boolean pinned,
                                                                @RequestParam Integer from,
                                                                @RequestParam Integer size) {
        List<CompilationDto> compilations = null;
        return ResponseEntity.ok(compilations);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<List<CompilationDto>> getCompilationsById(@PathVariable Integer compId) {
        List<CompilationDto> compilations = null;
        return ResponseEntity.ok(compilations);
    }
}