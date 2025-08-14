package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.UpdateEventUserRequest;
import ru.practicum.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventsController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getFullEvents(@RequestParam(required = false) List<Integer> users,
                                                        @RequestParam(required = false) List<String> states,
                                                        @RequestParam(required = false) List<Integer> categories,
                                                        @RequestParam(required = false) String rangeStart,
                                                        @RequestParam(required = false) String rangeEnd,
                                                        @RequestParam(defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        List<EventFullDto> events = eventService.getFullEvents(users, states, categories, rangeStart, rangeEnd, from, size);
        log.info("Список событий получен");
        return ResponseEntity.ok(events);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> update(@PathVariable Integer eventId,
                                               @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Обновленные данные: {}", updateEventUserRequest);
        EventFullDto event = eventService.updateEventAndStatus(updateEventUserRequest, eventId);
        log.info("Cобытие обновлено");
        return ResponseEntity.ok(event);
    }
}
