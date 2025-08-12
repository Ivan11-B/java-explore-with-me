package ru.practicum.service;

import ru.practicum.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationService {

    List<ParticipationRequestDto> getAllCurrentUser(Integer userId);

    ParticipationRequestDto saveRequest(Integer userId, Integer eventId);

    ParticipationRequestDto updateRequest(Integer userId, Integer requestId);
}
