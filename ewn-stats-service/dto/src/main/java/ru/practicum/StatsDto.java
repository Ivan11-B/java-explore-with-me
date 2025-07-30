package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsDto {

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private Long hits;
}