package ru.practicum.service;

import jakarta.servlet.http.HttpServletRequest;
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

    EventFullDto getFullEventByIdCurrentUser(Integer userId, Integer eventId);

    EventFullDto updateEventCurrentUser(Integer userId, Integer eventId, UpdateEventUserRequest eventUserRequest);

    Set<Event> getAllByIds(Set<Integer> ids);

    Event getEventById(Integer eventId);

    List<EventFullDto> getFullEvents(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd,
                                     Integer from, Integer size);

    EventFullDto updateEventAndStatus(UpdateEventUserRequest updateEventUserRequest, Integer eventId);

    List<Event> getEventsByCategory(Integer catId);

    List<EventShortDto> getAllEventsByFilters(String text, List<Integer> categories, Boolean paid, String rangeStart, String rangeEnd,
                                              Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest request);

    EventFullDto getPublishedEventById(Integer eventId, HttpServletRequest request);

}
