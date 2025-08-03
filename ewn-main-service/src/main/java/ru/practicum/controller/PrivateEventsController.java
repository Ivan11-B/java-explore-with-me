package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
public class PrivateEventsController {

    @GetMapping
    public ResponseEntity<EventShortDto> getEventsUser(@PathVariable Integer userId,
                                                       @RequestParam Integer from,
                                                       @RequestParam Integer size) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<EventFullDto> saveEvent(@RequestBody NewEventDto newEventDto,
                                                  @PathVariable Integer userId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getFullEventById(@PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable Integer userId,
                                                    @PathVariable Integer eventId,
                                                    @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<ParticipationRequestDto> getParticipation(@PathVariable Integer userId,
                                                                    @PathVariable Integer eventId) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> update(@PathVariable Integer userId,
                                                                @PathVariable Integer eventId,
                                                                @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return ResponseEntity.ok(null);
    }
}
