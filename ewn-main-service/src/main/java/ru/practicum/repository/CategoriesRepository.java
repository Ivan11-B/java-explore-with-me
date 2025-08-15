package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Category;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Integer> {

    @Query(value = """
            SELECT * FROM categories
            ORDER BY id
            LIMIT :size OFFSET :from""",
            nativeQuery = true)
    List<Category> findAllCategories(@Param("from") Integer from,
                                     @Param("size") Integer size);

    Boolean existsByNameAndIdNot(String name, Integer catId);
}
