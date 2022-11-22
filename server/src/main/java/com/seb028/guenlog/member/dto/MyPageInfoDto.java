package com.seb028.guenlog.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MyPageInfoDto {

    @Getter
    @Builder
    public static class Patch {
        @NotBlank(message = "닉네임은 필수 입력 항목입니다")
        @Pattern(regexp = "^([a-zA-Z가-힣]){1,8}$",
                message = "닉네임은 한글 최대 8글자까지 입력 가능합니다.")
        private String nickname;
        private String age;
        private Character gender;
        private Integer height;
        private Integer weight;
    }

    @Getter
    @Builder
    public static class Response {
        private String nickname;
        private String email;
        private String age;
        private Character gender;
        private Integer height;
        private Integer weight;
    }
}
