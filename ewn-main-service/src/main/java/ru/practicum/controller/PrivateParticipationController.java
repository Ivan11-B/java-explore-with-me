package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.service.ParticipationService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class PrivateParticipationController {

    private final ParticipationService participationService;

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getRequests(@PathVariable Integer userId) {
        List<ParticipationRequestDto> participationRequestDtos = participationService.getAllRequestCurrentUser(userId);
        log.info("Список текущих заявок пользователя получен");
        return ResponseEntity.ok(participationRequestDtos);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> saveRequest(@PathVariable Integer userId,
                                                               @RequestParam Integer eventId) {
        ParticipationRequestDto participationRequestDto = participationService.saveRequest(userId, eventId);
        log.info("Запрос от текущего пользователя на участие в событии добавлен");
        return ResponseEntity.status(HttpStatus.CREATED).body(participationRequestDto);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable Integer userId,
                                                                 @PathVariable Integer requestId) {
        ParticipationRequestDto participationRequestDto = participationService.cancelRequest(userId, requestId);
        log.info("Отмена своего запроса на участие в событии выполнена");
        return ResponseEntity.ok(participationRequestDto);
    }
}
