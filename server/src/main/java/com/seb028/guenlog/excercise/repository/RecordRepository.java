package com.seb028.guenlog.excercise.repository;

import com.seb028.guenlog.excercise.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
