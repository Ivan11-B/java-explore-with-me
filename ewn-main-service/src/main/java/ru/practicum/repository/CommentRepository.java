package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment findCommentByAuthorIdAndEvent(Integer userId, Integer eventId);


    @Query(value = """
            SELECT * FROM comments
            WHERE event_id = :eventId
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Comment> findAllByEvent(Integer eventId,
                                 @Param("from") Integer from,
                                 @Param("size") Integer size);
}
