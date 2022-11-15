package com.seb028.guenlog.exercise.repository;

import com.seb028.guenlog.exercise.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
