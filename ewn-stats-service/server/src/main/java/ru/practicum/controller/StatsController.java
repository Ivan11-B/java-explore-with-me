package ru.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitDto;
import ru.practicum.StatsDto;
import ru.practicum.service.StatsServiceImpl;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsServiceImpl service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> save(@Valid @RequestBody HitDto hitDto) {
        log.debug("Сохранение информации: {}", hitDto);
        HitDto savedHit = service.save(hitDto);
        log.info("Данные записаны: {}", savedHit);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @NotNull String start,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @NotNull String end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Получение статистики по посещениям");
        List<StatsDto> stats = service.get(start, end, uris, unique);
        return ResponseEntity.ok(stats);
    }
}