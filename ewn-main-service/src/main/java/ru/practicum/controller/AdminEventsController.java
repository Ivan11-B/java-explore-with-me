package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.UpdateEventUserRequest;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
public class AdminEventsController {

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getEvents(@RequestParam List<Integer> users,
                                                        @RequestParam List<String> states,
                                                        @RequestParam List<Integer> categories,
                                                        @RequestParam String rangeStart,
                                                        @RequestParam String rangeEnd,
                                                        @RequestParam Integer from,
                                                        @RequestParam Integer size) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> update(@PathVariable Integer eventId,
                                               @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return ResponseEntity.ok(null);
    }
}
