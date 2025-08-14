package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;

import java.time.LocalDateTime;
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

    Boolean existsAllByCategoryId(Integer catId);

    @Query(value = """
            SELECT * FROM events
            WHERE (CAST(:users AS TEXT) IS NULL OR user_id IN (:users))
            AND (CAST(:states AS TEXT) IS NULL OR state IN (:states))
            AND (CAST(:categories AS TEXT) IS NULL OR category_id IN (:categories))
            AND (CAST(:rangeStart AS TEXT) IS NULL OR event_date >= :rangeStart)
            AND (CAST(:rangeEnd AS TEXT) IS NULL OR event_date <= :rangeEnd)
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Event> findAllByFilterAdmin(@Param("users") List<Integer> users,
                                     @Param("states") List<String> states,
                                     @Param("categories") List<Integer> categories,
                                     @Param("rangeStart") LocalDateTime rangeStart,
                                     @Param("rangeEnd") LocalDateTime rangeEnd,
                                     @Param("from") Integer from,
                                     @Param("size") Integer size);

    @Query(value = """
            SELECT * FROM events
            WHERE (state = 'PUBLISHED')
            AND (CAST(:text AS TEXT) IS NULL OR (annotation ILIKE %:text% OR description ILIKE %:text%))
            AND (CAST(:categories AS TEXT) IS NULL OR category_id IN (:categories))
            AND (CAST(:paid AS TEXT) IS NULL OR paid IN (:paid))
            AND (CAST(:rangeStart AS TEXT) IS NULL OR event_date >= :rangeStart)
            AND (:onlyAvailable = false OR (participant_limit = 0 OR confirmed_request < participant_limit))
            AND (CAST(:rangeEnd AS TEXT) IS NULL OR event_date <= :rangeEnd)
            ORDER BY
                CASE WHEN :sort = 'eventDate' THEN event_date END,
                CASE WHEN :sort = 'views' THEN views END,
                id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Event> findAllByFilterPublic(@Param("text") String text,
                                      @Param("categories") List<Integer> categories,
                                      @Param(("paid")) Boolean paid,
                                      @Param("rangeStart") LocalDateTime rangeStart,
                                      @Param("rangeEnd") LocalDateTime rangeEnd,
                                      @Param(("onlyAvailable")) boolean onlyAvailable,
                                      @Param("sort") String sort,
                                      @Param("from") Integer from,
                                      @Param("size") Integer size);
}
