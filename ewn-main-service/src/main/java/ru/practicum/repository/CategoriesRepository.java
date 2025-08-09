package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Category;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    @Query(value = """
            SELECT * FROM categories
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Category> findAllCategories(@Param("from") Integer from,
                            @Param("size") Integer size);
}
