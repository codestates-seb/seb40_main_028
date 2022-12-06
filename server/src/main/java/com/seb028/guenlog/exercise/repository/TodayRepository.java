package com.seb028.guenlog.exercise.repository;


import com.seb028.guenlog.exercise.dto.CalendarResponseDto;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.member.util.MonthlyRecord;
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

    @Query(value = "SELECT t.today_id as todayId, t.due_date as dueDate,(CASE WHEN(SUM(CASE WHEN r.is_completed = TRUE THEN 1 ELSE 0 END))>=1 THEN 1 ELSE 0 END) AS completed FROM today as t \n" +
            "LEFT JOIN record as r \n" +
            "ON t.today_id = r.today_id\n" +
            "WHERE t.due_date LIKE %:date% and t.member_id = :memberId \n" +
            "group by t.today_id", nativeQuery = true)
    List<CalendarResponseDto> findByLikeDateAndMemberId(@Param("date") String date, @Param("memberId")  Long memberId);

    // 사용자의 한달동안 운동기록을 세서 최근 6개월 단위로 조회
    @Query(value = "SELECT MID(t.due_date,1,7) AS dates , (SUM(CASE WHEN r.is_completed = TRUE THEN 1 ELSE 0 END)) AS record FROM today as t \n" +
            "LEFT JOIN record as r \n" +
            "ON t.today_id = r.today_id \n" +
            "WHERE t.member_id = :memberId \n" +
            "GROUP BY MID(t.due_date,1,7) \n" +
            "HAVING dates >  MID((now() - interval 6 month),1,7) \n" +
            "ORDER BY dates", nativeQuery = true)
    List<MonthlyRecord> findRecentSixMonthsRecordByMemberId(@Param("memberId") Long memberId);

}
