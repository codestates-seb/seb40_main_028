package com.seb028.guenlog.exercise.repository;


import com.seb028.guenlog.exercise.entity.Today;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodayRepository extends JpaRepository<Today, Long> {


    @EntityGraph(attributePaths = {"member"})
    List<Today> findAll();

    @EntityGraph(attributePaths = {"member"})
    @Query(value = "SELECT t FROM Today t WHERE t.member.id = :memberId AND t.dueDate = :date")
    Today findByDateAndMemberId(@Param("date") LocalDate date, @Param("memberId")  Long memberId);

    @EntityGraph(attributePaths = {"member"})
    Optional<Today> findById(Long todayId);


}
