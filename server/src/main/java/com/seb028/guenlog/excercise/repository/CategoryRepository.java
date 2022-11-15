package com.seb028.guenlog.excercise.repository;

import com.seb028.guenlog.excercise.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
