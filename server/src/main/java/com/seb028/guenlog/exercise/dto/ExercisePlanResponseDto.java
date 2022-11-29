package com.seb028.guenlog.exercise.dto;

import lombok.Data;

import java.util.List;

public class ExercisePlanResponseDto {



    @Data
    public static class RecordBaseResponseDto {
        private long recordId;

        private String name;

    }

    @Data
    public static class RecordDetailResponseDto {

        private Long exerciseId;

        private String name;

        private String images;

        private List<ExercisePlanRequestDto.EachRecords> records;

    }

    @Data
    public static class RecordPostResponseDto {

        private Long recordId;

        private Long exerciseId;

        private String name;

        private List<ExercisePlanRequestDto.EachRecords> records;

    }


}
