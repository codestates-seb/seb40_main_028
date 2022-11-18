package com.seb028.guenlog.member.repository;

import com.seb028.guenlog.member.entity.MemberWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface MemberWeightRepository extends JpaRepository<MemberWeight, Long> {
    // 멤버의 최근 몸무게 기록들 최신순으로 조회
    @Query(value = "SELECT mw FROM MemberWeight mw WHERE mw.member.id = :memberId ORDER BY mw.regDate DESC")
    List<MemberWeight> findRecentByMemberId(@Param("memberId") long memberId);
}
