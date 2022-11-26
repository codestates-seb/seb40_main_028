package com.seb028.guenlog.member.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
// MyPage 객체에 대한 Response DTO 클래스
public class MyPageResponseDto {
    private List<MonthlyRecordDto> monthlyRecords;      // 달별 총 운동 횟수 DTO 객체 리스트
    private List<MonthlyWeightDto> monthlyWeights;      // 달별 총 몸무게 평균 DTO 객체 리스트

    @Builder
    @Getter
    public static class MonthlyRecordDto {      // 달별 총 운동 횟수 DTO 클래스
        String date;           // 한달 날짜
        Integer record;        // 한달 총 운동 횟수
    }

    @Builder
    @Getter
    public static class MonthlyWeightDto {      // 달별 총 몸무게 평균 DTO 클래스
        String date;        // 한달 날짜
        Double weight;      // 한달 몸무게 평균
    }
}
