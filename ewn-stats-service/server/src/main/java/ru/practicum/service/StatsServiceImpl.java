package ru.practicum.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.IntervalTimeException;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.HitDto;
import ru.practicum.StatsDto;
import ru.practicum.repository.StatsRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;
    private final HitMapper hitMapper;

    @Override
    @Transactional
    public HitDto save(HitDto hitDto) {
        Hit hit = hitMapper.toEntity(hitDto);
        Hit savesHit = repository.save(hit);
        log.debug("Данные сохраненные в БД: {}", savesHit);
        return hitMapper.toDto(savesHit);
    }

    @Override
    public List<StatsDto> get(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startTime = parseDate(start);
        LocalDateTime endTime = parseDate(end);
        if (startTime.isAfter(endTime)) {
            throw new IntervalTimeException("Дата старта должна быть раньше даты окончания");
        }
        if (unique) {
            return repository.findHitsBetweenStartAndEndUnique(startTime, endTime, uris);
        }
        return repository.findHitsBetweenStartAndEnd(startTime, endTime, uris);
    }

    private LocalDateTime parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }


}