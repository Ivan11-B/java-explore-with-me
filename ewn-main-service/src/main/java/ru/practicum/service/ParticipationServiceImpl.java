package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ParticipationException;
import ru.practicum.mapper.ParticipationMapper;
import ru.practicum.model.*;
import ru.practicum.repository.ParticipationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;

    private final UserService userService;
    private final EventService eventService;

    @Override
    public List<ParticipationRequestDto> getAllRequestCurrentUser(Integer userId) {
        List<Participation> participation = participationRepository.findAllByRequester(userId);
        return participationMapper.toDto(participation);
    }

    @Override
    @Transactional
    public ParticipationRequestDto saveRequest(Integer userId, Integer eventId) {
        if (participationRepository.findAllByEventAndRequester(eventId, userId) != null) {
            throw new ParticipationException("Запрос уже был сделан");
        }
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        if (user.getId() == event.getInitiator().getId()) {
            throw new ParticipationException("Инициатор события не может добавлять запрос на участие в своем событии");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ParticipationException("Данное событие не опубликованно");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequest()) {
            throw new ParticipationException("Достигнут лимит на участие");
        }
        Participation participation = Participation.builder()
                .requester(userId)
                .event(eventId)
                .created(LocalDateTime.now())
                .build();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            participation.setStatus(StateRequest.CONFIRMED);
            event.setConfirmedRequest(event.getConfirmedRequest() + 1);
        } else {
            participation.setStatus(StateRequest.PENDING);
        }
        Participation savedParticipation = participationRepository.save(participation);
        return participationMapper.toDto(savedParticipation);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        userService.getUserById(userId);
        Participation participation = getParticipationById(requestId);
        if (participation.getStatus() == StateRequest.CONFIRMED) {
            Event event = eventService.getEventById(participation.getEvent());
            event.setConfirmedRequest(event.getConfirmedRequest() - 1);
        }
        participation.setStatus(StateRequest.CANCELED);
        Participation updatedParticipation = participationRepository.save(participation);
        return participationMapper.toDto(updatedParticipation);
    }

    @Override
    public List<ParticipationRequestDto> getAllRequestCurrentEvent(Integer userId, Integer eventId) {
        validateOwnerEvent(userId, eventId);
        List<Participation> participation = participationRepository.findAllByEvent(eventId);
        return participationMapper.toDto(participation);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateStatus(Integer userId, Integer eventId, EventRequestStatusUpdateRequest updateRequest) {
        Event event = validateOwnerEvent(userId, eventId);
        StateRequest status = StateRequest.valueOf(updateRequest.getStatus());
        List<Participation> participation = participationRepository.findAllById(updateRequest.getRequestIds());
        if (status == StateRequest.REJECTED) {
            return rejectRequests(participation);
        } else {
            return confirmRequests(event, participation);
        }
    }

    @Override
    public Participation getParticipationById(Integer requestId) {
        return participationRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с id=" + requestId + " не найден"));
    }

    private Event validateOwnerEvent(Integer userId, Integer eventId) {
        userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        return event;
    }

    private EventRequestStatusUpdateResult rejectRequests(List<Participation> participation) {
        for (Participation request : participation) {
            if (request.getStatus() == StateRequest.CONFIRMED) {
                throw new ParticipationException("Нельзя отменить принятую заявку");
            }
            request.setStatus(StateRequest.REJECTED);
        }
        List<Participation> result = participationRepository.saveAll(participation);
        return EventRequestStatusUpdateResult.builder()
                .rejectedRequests(participationMapper.toDto(result))
                .build();
    }

    private EventRequestStatusUpdateResult confirmRequests(Event event, List<Participation> participation) {
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequest()) {
            throw new ParticipationException("Достигнут лимит по заявкам на данное событие");
        }
        Integer availableSlots = event.getParticipantLimit() - event.getConfirmedRequest();
        List<Participation> confirmedRequests = new ArrayList<>();
        List<Participation> rejectedRequests = new ArrayList<>();
        for (Participation request : participation) {
            if (availableSlots > 0) {
                request.setStatus(StateRequest.CONFIRMED);
                event.setConfirmedRequest(event.getConfirmedRequest() + 1);
                availableSlots--;
                confirmedRequests.add(request);
            } else {
                request.setStatus(StateRequest.REJECTED);
                rejectedRequests.add(request);
            }
        }
        participationRepository.saveAll(participation);
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(participationMapper.toDto(confirmedRequests))
                .rejectedRequests(participationMapper.toDto(rejectedRequests))
                .build();
    }
}