package ru.practicum.service;

import ru.practicum.dto.EventFullDto;

import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.dto.UpdateEventUserRequest;
import ru.practicum.model.Event;

import java.util.List;
import java.util.Set;

public interface EventService {

    EventFullDto saveEvent(NewEventDto newEventDto, Integer userId);

    List<EventShortDto> getEventsCurrentUser(Integer userId, Integer from, Integer size);

    EventFullDto getFullEventByUserId(Integer userId, Integer eventId);

    EventFullDto updateEventCurrentUser(Integer userId, Integer eventId, UpdateEventUserRequest eventUserRequest);

    Set<Event> getAllByIds(Set<Integer> ids);

    Event getById(Integer eventId);

    List<EventFullDto> searchEvents();

    EventShortDto updateEvent(Integer eventId);

    List<Event> getEventsByCategory(Integer catId);
}
