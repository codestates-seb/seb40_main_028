package com.seb028.guenlog.member.repository;

import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.util.MonthlyWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface MemberWeightRepository extends JpaRepository<MemberWeight, Long> {
    // 멤버의 최근 몸무게 기록들 최신순으로 조회
    @Query(value = "SELECT mw FROM MemberWeight mw WHERE mw.member.id = :memberId ORDER BY mw.regDate DESC")
    List<MemberWeight> findRecentByMemberId(@Param("memberId") long memberId);

    // 사용자의 한 달 동안의 몸무게를 평균내서 최근 6개월간 기록 조회
    @Query(value = "SELECT  MID(mw.created_at,1,7) AS dates , ROUND(AVG(mw.weight), 1)as weight \n" +
                    "FROM member_weight mw \n " +
                    "WHERE mw.member_id = :memberId \n" +
                    "GROUP BY MID(mw.created_at,1,7)  \n" +
                    "HAVING dates >  MID((now() - interval 6 month),1,7) \n",
                    nativeQuery = true)
    List<MonthlyWeight> findRecentSixMonthWeightByMemberId(@Param("memberId") Long memberId);


}
