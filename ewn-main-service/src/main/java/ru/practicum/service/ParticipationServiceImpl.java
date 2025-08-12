package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ParticipationException;
import ru.practicum.mapper.ParticipationMapper;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.Participation;
import ru.practicum.model.User;
import ru.practicum.repository.ParticipationRepository;

import java.time.LocalDateTime;
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
    public List<ParticipationRequestDto> getAllCurrentUser(Integer userId) {
        List<Participation> participations = participationRepository.findAllByRequester(userId);
        return participationMapper.toDtoList(participations);
    }

    @Override
    @Transactional
    public ParticipationRequestDto saveRequest(Integer userId, Integer eventId) {
        User user = userService.getUserById(userId);
        Event event = eventService.getById(eventId);
        if (user.getId() == event.getInitiator().getId()) {
            throw new ParticipationException("Инициатор события не может добавлять запрос на участие в своем событии");
        }
        if (event.getState() == EventState.PENDING || event.getState() == EventState.CANCELED) {
            throw new ParticipationException("Данное событие не опубликованно");
        }
        if (event.getRequestModeration() == true) {
            if (event.getParticipantLimit() == event.getConfirmedRequest()) {
                throw new ParticipationException("Достигнут лимит на участие");
            }
        }
        if (participationRepository.findAllByEventAndRequester(eventId, userId) == null) {
            Participation participation = Participation.builder()
                    .requester(userId)
                    .event(eventId)
                    .created(LocalDateTime.now())
                    .status(EventState.PENDING)
                    .build();
            Participation savedParticipation = participationRepository.save(participation);
            return participationMapper.toDto(savedParticipation);
        } else {
            throw new ParticipationException("Запрос уже был сделан");
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto updateRequest(Integer userId, Integer requestId) {
        User user = userService.getUserById(userId);
        Participation participation = participationRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос с id=" + requestId + " не найден"));
        if (user.getId() != participation.getRequester()) {
            new ParticipationException("Пользователь не владелец запроса");
        }
        participation.setStatus(EventState.CANCELED);
        Participation updatedParticipation = participationRepository.save(participation);
        return participationMapper.toDto(updatedParticipation);
    }
}