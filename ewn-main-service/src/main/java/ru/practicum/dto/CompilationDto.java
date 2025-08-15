package ru.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class CompilationDto {

    @NotNull
    private Integer id;

    @NotNull
    private Boolean pinned;

    @NotBlank
    private String title;

    private Set<EventShortDto> events;
}
