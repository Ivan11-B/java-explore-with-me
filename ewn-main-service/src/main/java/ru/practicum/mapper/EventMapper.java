package ru.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.Location;
import ru.practicum.dto.NewEventDto;
import ru.practicum.model.Constants;
import ru.practicum.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public Event toEntity(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), Constants.DATE_TIME_FORMATTER))
                .lat(newEventDto.getLocation().getLat())
                .lon(newEventDto.getLocation().getLon())
                .paid(newEventDto.getPaid() != null ? newEventDto.getPaid() : false)
                .participantLimit(newEventDto.getParticipantLimit() != null ? newEventDto.getParticipantLimit() : 0)
                .requestModeration(newEventDto.getRequestModeration() != null ? newEventDto.getRequestModeration() : true)
                .title(newEventDto.getTitle())
                .confirmedRequest(0)
                .views(0L)
                .build();
    }

    public EventFullDto toFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequest())
                .createdOn(formatDateTime(event.getCreatedOn()))
                .description(event.getDescription())
                .eventDate(formatDateTime(event.getEventDate()))
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .location(Location.builder()
                        .lat(event.getLat())
                        .lon(event.getLon())
                        .build())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(formatDateTime(event.getPublishedOn()))
                .requestModeration(event.getRequestModeration())
                .state(String.valueOf(event.getState()))
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public List<EventFullDto> toFullDto(List<Event> events) {
        return events.stream()
                .map(this::toFullDto)
                .collect(Collectors.toList());
    }

    public EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequest())
                .eventDate(formatDateTime(event.getEventDate()))
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public List<EventShortDto> toShortDto(List<Event> events) {
        return events.stream()
                .map(this::toShortDto)
                .collect(Collectors.toList());
    }

    public Set<EventShortDto> toShortDto(Set<Event> events) {
        return events.stream()
                .map(this::toShortDto)
                .collect(Collectors.toSet());
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return localDateTime.format(Constants.DATE_TIME_FORMATTER);
        } else {
            return null;
        }
    }
}