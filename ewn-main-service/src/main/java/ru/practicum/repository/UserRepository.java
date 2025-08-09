package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = """
            SELECT * FROM users
            WHERE id IN (:ids)
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<User> findUserByIds(@Param("ids") List<Integer> ids,
                             @Param("from") Integer from,
                             @Param("size") Integer size);

    @Query(value = """
            SELECT * FROM users
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<User> findAllUsers(@Param("from") Integer from,
                             @Param("size") Integer size);
}
