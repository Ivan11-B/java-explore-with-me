package ru.practicum.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.dto.UpdateEventUserRequest;
import ru.practicum.exception.EventDateException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.OwnerEventException;
import ru.practicum.exception.UpdateConflictException;
import ru.practicum.mapper.EventMapper;

import ru.practicum.model.*;
import ru.practicum.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        validateEventDate(LocalDateTime.parse(newEventDto.getEventDate(), formatter));
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
    public EventFullDto getFullEventByIdCurrentUser(Integer userId, Integer eventId) {
        Event event = validateOwnerEventAndReturn(userId, eventId);
        return eventMapper.toFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto updateEventCurrentUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEvent) {
        Event event = validateOwnerEventAndReturn(userId, eventId);
        if (event.getState() == EventState.PUBLISHED) {
            throw new UpdateConflictException("Изменить можно только отмененные события или события в состоянии ожидания модерации");
        }
        if (updateEvent.getEventDate() != null) {
            LocalDateTime newEventDate = LocalDateTime.parse(updateEvent.getEventDate(), formatter);
            validateEventDate(newEventDate);
            event.setEventDate(newEventDate);
        }
        if (updateEvent.getStateAction() != null) {
            StateAction action = StateAction.valueOf(updateEvent.getStateAction());
            if (action == StateAction.SEND_TO_REVIEW) {
                event.setState(EventState.PENDING);
            } else if (action == StateAction.CANCEL_REVIEW) {
                event.setState(EventState.CANCELED);
            } else if (action == StateAction.PUBLISH_EVENT) {
                event.setState(EventState.PUBLISHED);
            } else {
                throw new UpdateConflictException("Неизвестная команда: " + action);
            }
        }
        updateFields(event, updateEvent);
        Event updatedEvent = eventRepository.save(event);
        return eventMapper.toFullDto(updatedEvent);
    }

    @Override
    public Set<Event> getAllByIds(Set<Integer> ids) {
        return new HashSet<>(eventRepository.findAllById(ids));
    }

    @Override
    public Event getEventById(Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено"));
    }

    @Override
    public List<EventFullDto> getFullEvents(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd,
                                            Integer from, Integer size) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (rangeStart != null) {
            start = LocalDateTime.parse(rangeStart, formatter);
        }
        if (rangeEnd != null) {
            end = LocalDateTime.parse(rangeEnd, formatter);
            if (start != null && end.isBefore(start)) {
                throw new EventDateException("Дата конца должна быть позже даты начала");
            }
        }
        List<Event> events = eventRepository.findAllByFilterAdmin(users, states, categories, start, end,from, size);
        return eventMapper.toFullDto(events);
    }

    @Override
    @Transactional
    public EventFullDto updateEventAndStatus(UpdateEventUserRequest updateEventUserRequest, Integer eventId) {
        if (updateEventUserRequest.getEventDate() != null) {
            validateEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate(), formatter));
        }
        Event event = getEventById(eventId);
        if (updateEventUserRequest.getStateAction() != null) {
            StateAction stateAction = StateAction.valueOf(updateEventUserRequest.getStateAction());
            if (stateAction == StateAction.PUBLISH_EVENT) {
                if (event.getEventDate().minusHours(1L).isBefore(LocalDateTime.now())) {
                    throw new EventDateException("Дата начала изменяемого события должна быть не ранее чем за час от даты публикации");
                }
                if (event.getState() != EventState.PENDING) {
                    throw new UpdateConflictException("Не удается опубликовать событие, не имеющее статуса ожидающего");
                }
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else if (stateAction == StateAction.REJECT_EVENT) {
                if (event.getState() == EventState.PUBLISHED) {
                    throw new UpdateConflictException("Невозможно отменить опубликованное событие");
                }
                event.setState(EventState.CANCELED);
            } else if (stateAction == StateAction.CANCEL_REVIEW) {
                event.setState(EventState.CANCELED);
            }
        }
        updateFields(event, updateEventUserRequest);
        Event updatedEvent = eventRepository.save(event);
        return eventMapper.toFullDto(updatedEvent);
    }

    @Override
    public List<Event> getEventsByCategory(Integer catId) {
        return eventRepository.findAllByCategoryId(catId);
    }

    @Override
    public List<EventShortDto> getAllEventsByFilters(String text, List<Integer> categories, Boolean paid, String rangeStart,
                                             String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (rangeStart != null) {
            start = LocalDateTime.parse(rangeStart, formatter);
        }
        if (rangeStart != null) {
            end = LocalDateTime.parse(rangeEnd, formatter);
            if (start != null && end.isBefore(start)) {
                throw new EventDateException("Дата конца должна быть позже даты начала");
            }
        }
        List<Event> events = eventRepository.findAllByFilterPublic(text, categories, paid, start, end, onlyAvailable, from, size);
        return eventMapper.toShortDto(events);
    }

    @Override
    public EventFullDto getPublishedEventById(Integer eventId) {
        Event event = getEventById(eventId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            return eventMapper.toFullDto(event);
        } else {
            throw new NotFoundException("Событие с id=" + eventId + " не найдено");
        }
    }

    private void validateEventDate(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(MIN_HOURS_BEFORE_EVENT))) {
            throw new EventDateException("Дата события должна быть минимум на 2 часа позже текущего времени");
        }
    }

    private Event validateOwnerEventAndReturn(Integer userId, Integer eventId) {
        userService.getUserById(userId);
        Event event = getEventById(eventId);
        if (userId != event.getInitiator().getId()) {
            throw new OwnerEventException("User не является инициатором события");
        }
        return event;
    }

    private void updateFields(Event event, UpdateEventUserRequest updateEvent) {
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
    }

}
