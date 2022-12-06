package com.seb028.guenlog.exercise.repository;

import com.seb028.guenlog.exercise.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = {"exercises"})
    List<Category> findAll();
}
