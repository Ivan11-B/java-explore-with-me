package ru.practicum.service;

import ru.practicum.HitDto;
import ru.practicum.StatsDto;

import java.util.List;

public interface StatsService {
    HitDto save(HitDto hitDto);

    List<StatsDto> get(String start, String end, List<String> uris, Boolean unique);
}
