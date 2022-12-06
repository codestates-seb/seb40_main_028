package com.seb028.guenlog.exercise.dto;

import lombok.Data;

import java.util.List;

public class ExerciseMainResponseDto {

    @Data
    public static class ExerciseDto {

        private Long exerciseId;

        private String exerciseName;

        private Boolean isComleted;

    }

    @Data
    public static class RecordDetailDto {

        private Integer totalTime;

        private List<ExerciseDto> exercises;

    }
}
