package ru.practicum.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CompilationMapper {

    private final EventMapper eventMapper;


    public Compilation toEntity(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned() != null ? newCompilationDto.getPinned() : false)
                .events(events)
                .build();
    }

//    public Compilation toEntity(UpdateCompilationRequest updateCompilation, Set<Event> events) {
//        return Compilation.builder()
//                .title(updateCompilation.getTitle())
//                .pinned(updateCompilation.getPinned() != null ? updateCompilation.getPinned() : false)
//                .events(events)
//                .build();
//    }

    public CompilationDto toDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(eventMapper.toShortDto(compilation.getEvents()))
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
