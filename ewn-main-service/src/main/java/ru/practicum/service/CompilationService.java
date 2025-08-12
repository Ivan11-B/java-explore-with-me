package ru.practicum.service;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.model.Compilation;

import java.util.List;

public interface CompilationService {

    CompilationDto saveCompilation(NewCompilationDto newCompilationDto);

    CompilationDto updateCompilation(UpdateCompilationRequest updateCompilation, Integer compId);

    void deleteCompilation(Integer compId);

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationDtoById(Integer compId);

    Compilation getCompilationById(Integer compId);
}
