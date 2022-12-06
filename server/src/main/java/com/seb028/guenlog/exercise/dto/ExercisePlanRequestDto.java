package com.seb028.guenlog.exercise.dto;

import lombok.Data;

import java.util.List;

public class ExercisePlanRequestDto {


    @Data
    public static class EachRecords {

        private Integer weight;

        private Integer count;

        private Integer timer;

        private Boolean eachCompleted;

    }

    @Data
    public static class TodoPostDto {

        private Long exerciseId;

        private List<EachRecords> eachRecords;
    }





}
