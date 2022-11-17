package com.seb028.guenlog.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {

    @AllArgsConstructor
    @Getter
    public static class post {
        @NotBlank(message = "이메일은 필수 입력 항목입니다")
        @Email(message = "이메일 형식이 맞지 않습니다")
        private String email;

        @NotBlank(message = "닉네임은 필수 입력 항목입니다")
        @Pattern(regexp = "^([a-zA-Z가-힣]){1,8}$",
                message = "닉네임은 한글 최대 8글자까지 입력 가능합니다.")
        private String nickname;

        @NotBlank(message = "비밀번호를 필수 입력 항목입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,15}$",
                message = "비밀번호는 영문자, 숫자, 특수기호를 포함해 최소 10자, 최대 15자 까지 입력 가능합니다.")
        private String password;
    }

}

