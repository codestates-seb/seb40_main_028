package com.seb028.guenlog.exercise.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
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

    @Data
    @NoArgsConstructor
    public static class CalendarDto {
        private Long todayId;
        private LocalDate dueDate;
        private Integer completed;


        public CalendarDto(Long todayId, LocalDate dueDate, Integer completed) {
            this.todayId = todayId;
            this.dueDate = dueDate;
            this.completed = completed;
        }
    }
}
