package com.mstra.app.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
    @Query("""
            SELECT t FROM Todo t
            WHERE t.user.id = :userId
            AND t.startDate = CURRENT_DATE
            """)
    List<Todo> findAllByUserId(String userId);

    List<Todo> findAllByUserIdAndCategoryId(String userId, String catId);

    @Query("""
            SELECT t FROM Todo t
            WHERE t.endDate >= CURRENT_DATE AND t.endTime >= CURRENT_TIME
            AND t.user.id = :userId
            """)
    List<Todo> findAllDueTodos(String userId);
}
