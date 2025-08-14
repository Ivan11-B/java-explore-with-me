package ru.practicum.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.service.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationsController {

    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> saveCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = compilationService.saveCompilation(newCompilationDto);
        log.info("Подборка событий добавлена");
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable Integer compId) {
        compilationService.deleteCompilation(compId);
        log.info("Подборка событий удалена");
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable Integer compId,
                                                            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("Подборка событий {}", updateCompilationRequest);
        CompilationDto compilationDto = compilationService.updateCompilation(updateCompilationRequest, compId);
        log.info("Подборка событий обновлена");
        return ResponseEntity.ok(compilationDto);
    }
}
