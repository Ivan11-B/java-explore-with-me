package ru.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.model.Compilation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;


    public Compilation toEntity(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned() != null ? newCompilationDto.getPinned() : false)
                .build();
    }


    public CompilationDto toDto(Compilation compilation) {
        Set<EventShortDto> eventShortDto;
        if (compilation.getEvents() != null) {
            eventShortDto = eventMapper.toShortDto(compilation.getEvents());
        } else {
            eventShortDto = null;
        }
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(eventShortDto)
                .build();
    }

    public Set<CompilationDto> toDto(Set<Compilation> compilations) {
        return compilations.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public List<CompilationDto> toDto(List<Compilation> compilations) {
        return compilations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
