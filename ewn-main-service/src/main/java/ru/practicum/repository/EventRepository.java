package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = """
            SELECT * FROM events
            WHERE user_id = :userId
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Event> findAllByInitiator(@Param("userId") Integer userId,
                                   @Param("from") Integer from,
                                   @Param("size") Integer size);

    List<Event> findAllByCategoryId(Integer catId);
}
