package com.seb028.guenlog.exercise.repository;

import com.seb028.guenlog.exercise.entity.Record;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {

    @EntityGraph(attributePaths = {"exercise","today"})
    List<Record> findAll();

    @EntityGraph(attributePaths = {"exercise","today"})
    Optional<Record> findById(Long recordId);
    @EntityGraph(attributePaths = {"exercise","today"})
    @Query("SELECT r FROM Record r WHERE r.today.id = :todayId")
    List<Record> findByTodayId(@Param("todayId")  Long todayId);
}
