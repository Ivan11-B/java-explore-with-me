package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Compilation;
import ru.practicum.model.Event;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    @Query(value = """
            SELECT * FROM compilations
            WHERE pinned = :pinned
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Compilation> findAllByPinned(@Param("pinned") Boolean pinned,
                                   @Param("from") Integer from,
                                   @Param("size") Integer size);


    @Query(value = """
            SELECT * FROM compilations
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Compilation> findAll(@Param("from") Integer from,
                              @Param("size") Integer size);
}