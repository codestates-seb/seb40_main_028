package com.seb028.guenlog.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MyPageInfoDto {
    @Getter
    @Builder
    public static class Response {
        private String nickname;
        private Character gender;
        private Integer height;
        private Integer weight;
        private Integer age;
    }
}
