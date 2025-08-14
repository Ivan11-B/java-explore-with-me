package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.service.EventService;
import ru.practicum.service.ParticipationService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventsController {

    private final EventService eventService;
    private final ParticipationService participationService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEventsUser(@PathVariable Integer userId,
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @RequestParam(defaultValue = "10") Integer size) {
        List<EventShortDto> events = eventService.getEventsCurrentUser(userId, from, size);
        log.info("Список событий, добавленных текущим пользователем получен");
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventFullDto> saveEvent(@Valid @RequestBody NewEventDto newEventDto,
                                                  @PathVariable Integer userId) {
        log.info("Новое событие {}", newEventDto);
        EventFullDto eventFullDto = eventService.saveEvent(newEventDto, userId);
        log.info("Событие добавлено");
        return ResponseEntity.status(HttpStatus.CREATED).body(eventFullDto);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getFullEventByIdCurrentUser(@PathVariable Integer userId,
                                                         @PathVariable Integer eventId) {
        EventFullDto eventFullDto = eventService.getFullEventByIdCurrentUser(userId, eventId);
        log.info("Полная информация о событии, добавленного текущим пользователем получено");
        return ResponseEntity.ok(eventFullDto);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable Integer userId,
                                                    @PathVariable Integer eventId,
                                                    @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("исходное dto: {}", updateEventUserRequest);
        EventFullDto eventFullDto = eventService.updateEventCurrentUser(userId, eventId, updateEventUserRequest);
        log.info("Изменено событие, добавленного текущим пользователем");
        return ResponseEntity.ok(eventFullDto);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getParticipation(@PathVariable Integer userId,
                                                                    @PathVariable Integer eventId) {
        List<ParticipationRequestDto> participationRequestDto = participationService.getAllRequestCurrentEvent(userId, eventId);
        log.info("Информация о запросах на участие в событии текущего пользователя получена");
        return ResponseEntity.ok(participationRequestDto);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> update(@PathVariable Integer userId,
                                                                @PathVariable Integer eventId,
                                                                @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        EventRequestStatusUpdateResult result = participationService.updateStatus(userId, eventId, updateRequest);
        log.info("Изменение статуса заявок на участие в событие текущего пользователя выполнено");
        return ResponseEntity.ok(result);
    }
}