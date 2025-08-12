package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.repository.EventRepository;

@Service
@RequiredArgsConstructor
public class ControlReferenceEventsService {

    private final EventRepository eventRepository;


    public Boolean getEventByCategory(Integer catId) {
        if (eventRepository.findAllByCategoryId(catId).stream().count() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
