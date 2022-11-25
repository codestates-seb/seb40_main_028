package com.seb028.guenlog.exercise.mapper;


import com.seb028.guenlog.exercise.dto.ExerciseProgressResponseDto;
import com.seb028.guenlog.exercise.util.ExerciseProgress;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseProgressMapper {

    /**
     * 운동 내역, 기록 목록 객체 ExerciseProgress 객체를 ResponseDto 클래스로 변환
     * @param exerciseProgress : 오늘 하루 운동 내역, 운동 기록 목록 저장
     * @return ExerciseProgressResponseDto - 응답용 객체 
     */
    default ExerciseProgressResponseDto exerciseProgressToExerciseProgressResponseDto(ExerciseProgress exerciseProgress) {
        // 오늘 하루 운동 내역 저장 
        long todayId = exerciseProgress.getToday().getId();                 // 오늘 하루 운동 id 저장
        int totalTime = exerciseProgress.getToday().getTotalTime();         // 오늘 하루 운동 총 시간 저장 
        
        // 오늘 하루 운동 기록 목록 리스트 저장 
        List<ExerciseProgressResponseDto.TodayExerciseDto> exercises = exerciseProgress.getRecords().stream()
                .map(record -> {
                    ExerciseProgressResponseDto.TodayExerciseDto todayExerciseDto =
                            ExerciseProgressResponseDto.TodayExerciseDto.builder()
                                .exerciseId(record.getExercise().getId())       // 각 운동 ID
                                .isCompleted(record.getIsCompleted())           // 각 운동 완료 여부 
                                .exerciseName(record.getExercise().getName())   // 각 운동 이름
                                .imageUrl(record.getExercise().getImageUrl())   // 이미지 URL
                                .eachRecords(record.getEachRecords())           // 각 세트(횟수, 무게, 타이머, 완료여부)
                                .build();
                    return todayExerciseDto;
                }).collect(Collectors.toList());            

        // ExerciseProgressResponseDto로 변환해서 반환
        return ExerciseProgressResponseDto.builder()
                .todayId(todayId)               // 오늘 하루 운동 내역 ID
                .totalTime(totalTime)           // 오늘 하루 운동 내역 총 운동 시간
                .exercises(exercises)           // 오늘 하루 운동 기록 목록
                .build();
    }
}
