package ru.practicum.mapper;

import jakarta.persistence.Column;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.model.Participation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ParticipationMapper {

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ParticipationRequestDto toDto(Participation participation) {
        return ParticipationRequestDto.builder()
                .id(participation.getId())
                .event(participation.getEvent())
                .requester(participation.getRequester())
                .status(String.valueOf(participation.getStatus()))
                .created(formatDateTime(participation.getCreated()))
                .build();
    }

    public List<ParticipationRequestDto> toDto(List<Participation> participation) {
        return  participation.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return localDateTime.format(formatter);
        } else {
            return null;
        }
    }
}
