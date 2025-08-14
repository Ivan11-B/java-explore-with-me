package ru.practicum.service;

import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.model.Participation;

import java.util.List;

public interface ParticipationService {

    List<ParticipationRequestDto> getUserParticipation(Integer userId);

    ParticipationRequestDto saveRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelRequest(Integer userId, Integer requestId);

    List<ParticipationRequestDto> getAllParticipation(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateStatusParticipation(Integer userId, Integer eventId, EventRequestStatusUpdateRequest updateRequest);

    Participation getParticipationById(Integer requestId);
}
