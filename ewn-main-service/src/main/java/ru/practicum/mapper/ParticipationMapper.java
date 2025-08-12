package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.model.Participation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {

    ParticipationRequestDto toDto(Participation participation);

    List<ParticipationRequestDto> toDtoList(List<Participation> participation);
}
