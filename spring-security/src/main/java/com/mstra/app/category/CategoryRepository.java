package com.mstra.app.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("""
            SELECT COUNT (c) > 0
            FROM Category c
            WHERE LOWER(c.name) = LOWER(:name)
            AND c.createdBy = :userId OR c.createdBy = 'APP'
            """)
    boolean findByNameAndUser(String name, String userId);

    @Query("""
            SELECT c FROM Category c
            WHERE c.createdBy = :userId OR c.createdBy = 'APP'
            """)
    List<Category> findAllByUserId(String userId);

    @Query("""
            SELECT c FROM Category c
            WHERE c.id = :catId
            AND (c.createdBy = :userId OR c.createdBy = 'APP')
            """)
    Optional<Category> findByIdAndUserId(String catId, String userId);
}
