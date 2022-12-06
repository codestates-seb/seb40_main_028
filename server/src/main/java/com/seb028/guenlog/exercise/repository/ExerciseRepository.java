package com.seb028.guenlog.exercise.repository;


import com.seb028.guenlog.exercise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
