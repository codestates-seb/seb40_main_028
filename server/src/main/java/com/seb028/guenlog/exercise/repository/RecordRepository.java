package com.seb028.guenlog.exercise.repository;

import com.seb028.guenlog.exercise.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
