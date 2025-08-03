package ru.practicum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ParticipationRequestDto;

@RestController
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestsController {

    @GetMapping
    public ResponseEntity<ParticipationRequestDto> getRequests(@PathVariable Integer userId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> saveRequest(@PathVariable Integer userId,
                                                               @RequestParam Integer eventId) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> updateRequest(@PathVariable Integer userId,
                                                                 @PathVariable Integer requestId) {
        return ResponseEntity.ok(null);
    }
}
