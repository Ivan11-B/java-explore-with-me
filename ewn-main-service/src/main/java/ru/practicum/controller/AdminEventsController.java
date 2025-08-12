package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
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
    public ResponseEntity<List<EventFullDto>> getEvents(@RequestParam List<Integer> users,
                                                        @RequestParam List<String> states,
                                                        @RequestParam List<Integer> categories,
                                                        @RequestParam String rangeStart,
                                                        @RequestParam String rangeEnd,
                                                        @RequestParam(defaultValue = "0") Integer from,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        List<EventFullDto> events = eventService.searchEvents();
        log.info("Список событий получен");
        return ResponseEntity.ok(events);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventShortDto> update(@PathVariable Integer eventId,
                                               @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        EventShortDto eventShortDto = eventService.updateEvent(eventId);
        log.info("Cобытие обновлено");
        return ResponseEntity.ok(eventShortDto);
    }
}
