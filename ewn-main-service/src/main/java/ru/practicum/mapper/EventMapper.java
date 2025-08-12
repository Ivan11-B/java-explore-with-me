package ru.practicum.mapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import ru.practicum.dto.*;

import ru.practicum.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event toEntity(NewEventDto newEventDto) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), formatter))
                .lat(newEventDto.getLocation().getLat())
                .lon(newEventDto.getLocation().getLon())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .build();
    }

    public EventFullDto toFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
//                .confirmedRequests(event.)
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
//                .views()
                .build();
    }

    public EventShortDto toShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toDto(event.getCategory()))
//                .confirmedRequests(event.)
                .eventDate(formatDateTime(event.getEventDate()))
                .initiator(userMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
//                .views()
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
            return localDateTime.format(formatter);
        } else {
            return null;
        }
    }



}
