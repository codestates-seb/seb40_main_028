package com.seb028.guenlog.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MyPageDto {

    @Builder
    @Getter
    public static class Response {
        private String[] months;
        private int[] monthlyExercises;
        private int[] monthlyWeights;
    }
}
