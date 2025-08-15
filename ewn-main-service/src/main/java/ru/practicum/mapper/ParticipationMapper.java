package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.model.Constants;
import ru.practicum.model.Participation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ParticipationMapper {

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
        return participation.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private String formatDateTime(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return localDateTime.format(Constants.DATE_TIME_FORMATTER);
        } else {
            return null;
        }
    }
}
