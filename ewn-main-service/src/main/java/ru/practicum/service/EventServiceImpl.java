package ru.practicum.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.dto.UpdateEventUserRequest;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.OwnerException;
import ru.practicum.exception.UpdateConflictException;
import ru.practicum.mapper.EventMapper;

import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;
    private final CategoriesService categoriesService;

    private static final int MIN_HOURS_BEFORE_EVENT = 2;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    @Transactional
    public EventFullDto saveEvent(NewEventDto newEventDto, Integer userId) {
        User initiator = userService.getUserById(userId);
        Category category = categoriesService.getCategoryById(newEventDto.getCategory());
        Event event = eventMapper.toEntity(newEventDto);
        event.setInitiator(initiator);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toFullDto(savedEvent);
    }

    @Override
    public List<EventShortDto> getEventsCurrentUser(Integer userId, Integer from, Integer size) {
        List<Event> events = eventRepository.findAllByInitiator(userId, from, size);
        return eventMapper.toShortDto(events);
    }

    @Override
    public EventFullDto getFullEventByUserId(Integer userId, Integer eventId) {
        User initiator = userService.getUserById(userId);
        Event event = findEventById(eventId);
        if (initiator.getId() == event.getInitiator().getId()) {
            return eventMapper.toFullDto(event);
        } else {
            throw new OwnerException("User не является инициатором события");
        }
    }

    @Override
    public EventFullDto updateEventCurrentUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEvent) {
        userService.getUserById(userId);
        Event event = findEventById(eventId);
        LocalDateTime newDate = null;
        if (updateEvent.getEventDate() != null) {
            newDate = LocalDateTime
                    .parse(updateEvent.getEventDate(), formatter).minusHours(MIN_HOURS_BEFORE_EVENT);
            if (newDate.isBefore(event.getEventDate())) {
                throw new UpdateConflictException("дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента");
            }
        }
        if (event.getState() == EventState.CANCELED || event.getState() == EventState.PENDING) {
            if (updateEvent.getAnnotation() != null) {
                event.setAnnotation(updateEvent.getAnnotation());
            }
            if (updateEvent.getCategory() != null) {
                event.setCategory(categoriesService.getCategoryById(updateEvent.getCategory()));
            }
            if (updateEvent.getDescription() != null) {
                event.setDescription(updateEvent.getDescription());
            }
            if (updateEvent.getLocation() != null) {
                event.setLat(updateEvent.getLocation().getLat());
                event.setLon(updateEvent.getLocation().getLon());
            }
            if (updateEvent.getPaid() != null) {
                event.setPaid(updateEvent.getPaid());
            }
            if (updateEvent.getParticipantLimit() != null) {
                event.setParticipantLimit(updateEvent.getParticipantLimit());
            }
            if (updateEvent.getRequestModeration() != null) {
                event.setRequestModeration(updateEvent.getRequestModeration());
            }
            if (updateEvent.getTitle() != null) {
                event.setTitle(updateEvent.getTitle());
            }
            if (updateEvent.getStateAction() != null) {

            }
            event.setEventDate(newDate);
        } else {
            throw new UpdateConflictException("Изменить можно только отмененные события или события в состоянии ожидания модерации");
        }

        return eventMapper.toFullDto(event);
    }

    @Override
    public Set<Event> getAllByIds(Set<Integer> ids) {
        return new HashSet<>(eventRepository.findAllById(ids));
    }

    @Override
    public Event getById(Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено"));
    }

    @Override
    public List<EventFullDto> searchEvents() {
        return null;
    }

    @Override
    public EventShortDto updateEvent(Integer eventId) {
        return null;
    }

    @Override
    public List<Event> getEventsByCategory(Integer catId) {
        return eventRepository.findAllByCategoryId(catId);
    }

    private Event findEventById(Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено"));
    }
}
