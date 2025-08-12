package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Participation;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {

    Participation findAllByEventAndRequester(Integer eventId, Integer userId);

    List<Participation> findAllByRequester(Integer userId);


}
