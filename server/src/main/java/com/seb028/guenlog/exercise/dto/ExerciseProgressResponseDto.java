package com.seb028.guenlog.exercise.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
// 오늘 하루 운동 내역, 운동 기록 목록 저장한 ExerciseProgress의 응답용 객체
public class ExerciseProgressResponseDto {
    private Long todayId;                       // 오늘 하루 운동 내역의 ID
    private Integer totalTime;                      // 오늘 하루 운동 내역의 총 운동 시간
    List<TodayExerciseDto> exercises;           // 운동 기록 목록 리스트

    @Getter
    @Setter
    @Builder
    // 하루 운동 기록 응답용 객체
    public static class TodayExerciseDto {
        private Long exerciseId;                // 운동 ID
        private Boolean isCompleted;            // 완료 여부
        private String exerciseName;            // 운동 이름
        private String imageUrl;                // 이미지 UR
        private List<ExercisePlanRequestDto.EachRecords> eachRecords;   // 각 세트 (횟수, 무게, 타이머, 완료 여부)
    }
}
