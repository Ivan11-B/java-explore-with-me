package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;
import ru.practicum.repository.CompilationRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final EventService eventService;
    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;


    @Override
    @Transactional
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.toEntity(newCompilationDto);
        if (newCompilationDto.getEvents() != null) {
            Set<Event> events = eventService.getAllByIds(newCompilationDto.getEvents());
            compilation.setEvents(events);
        }
        Compilation savedCompilation = compilationRepository.save(compilation);
        return compilationMapper.toDto(savedCompilation);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(UpdateCompilationRequest updateCompilation, Integer compId) {
        Compilation compilation = getCompilationById(compId);
        if (updateCompilation.getTitle() != null) {
            compilation.setTitle(updateCompilation.getTitle());
        }
        if (updateCompilation.getPinned() != null) {
            compilation.setPinned(updateCompilation.getPinned());
        }
        if (updateCompilation.getEvents() != null) {
            for (Event event : compilation.getEvents()) {
                event.getCompilations().remove(compilation);
            }
            compilation.getEvents().clear();
            Set<Event> events = eventService.getAllByIds(updateCompilation.getEvents());
            for (Event event : events) {
                compilation.getEvents().add(event);
                event.getCompilations().add(compilation);
            }
        }
        Compilation updatedCompilation = compilationRepository.save(compilation);
        return compilationMapper.toDto(updatedCompilation);
    }

    @Override
    @Transactional
    public void deleteCompilation(Integer compId) {
        isExists(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public List<CompilationDto> getAllCompilation(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(from, size);
        } else {
            compilations = compilationRepository.findAllByPinned(pinned, from, size);
        }
        return compilationMapper.toDto(compilations);
    }

    @Override
    public Compilation getCompilationById(Integer compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка с id=" + compId + " не найдена"));
    }

    @Override
    public CompilationDto getCompilationDtoById(Integer compId) {
        return compilationMapper.toDto(getCompilationById(compId));
    }

    private void isExists(Integer compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new NotFoundException("Подборка с id=" + compId + " не найдена");
        }
    }
}
