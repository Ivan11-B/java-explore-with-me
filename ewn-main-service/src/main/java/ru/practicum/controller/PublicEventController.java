package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventShortDto;

import java.util.List;

@RestController
@RequestMapping("/events")
public class PublicEventController {

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEvents(@RequestParam String text,
                                                         @RequestParam List<Integer> categories,
                                                         @RequestParam Boolean paid,
                                                         @RequestParam String rangeStart,
                                                         @RequestParam String rangeEnd,
                                                         @RequestParam Boolean onlyAvailable,
                                                         @RequestParam String sort,
                                                         @RequestParam Integer from,
                                                         @RequestParam Integer size) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventShortDto> getEventById(@PathVariable Integer eventId) {
        return ResponseEntity.ok(null);
    }
}
