package com.seb028.guenlog.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MyPageInfoDto {

    @Getter
    @Builder
    public static class Patch {
        @Pattern(regexp = "^([a-zA-Z0-9가-힣]){2,10}$",
                message = "닉네임은 영문자, 한글, 숫자를 포함해 2자~10자 까지 입력 가능합니다.")
        private String nickname;

        @Pattern(regexp = "^(\\d){4}+-(\\d){2}+-(\\d){2}$",
                message = "생년월일은 8자리를 입력해 주세요")
        private String age;
        private Character gender;

        @Range(min = 0, max = 1000)
        private Integer height;

        @Range(min = 0, max = 1000)
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
