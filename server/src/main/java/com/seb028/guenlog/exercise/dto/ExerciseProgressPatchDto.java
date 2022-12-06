package com.seb028.guenlog.exercise.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@Getter
@Builder
// 운동 진행 후 완료 요청용 Patch Dto 클래스
public class ExerciseProgressPatchDto {
    private Integer totalTime;          // 오늘 하루 운동 내역 총 운동 시간
    private List<TodayExerciseDto> exercises;       // 운동 기록 목록 리스트

    @Getter
    @Builder
    // 하루 운동 기록 응답용 객체
    public static class TodayExerciseDto {
        private Long exerciseId;        // 운동 ID
        private Boolean isCompleted;    // 완료 여부
        private List<ExercisePlanRequestDto.EachRecords> eachRecords;     // 각 세트 (횟수, 무게, 타이머, 완료여부)
    }
}