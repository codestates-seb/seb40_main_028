package com.seb028.guenlog.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public class MemberDto {

    @AllArgsConstructor
    @Getter
    public static class post {
        @NotBlank(message = "이메일은 필수 입력 항목입니다")
        @Email(message = "이메일 형식이 맞지 않습니다")
        private String email;

        @NotBlank(message = "닉네임은 필수 입력 항목입니다")
        @Pattern(regexp = "^([a-zA-Z0-9가-힣]){2,10}$",
                message = "닉네임은 영문자, 한글, 숫자를 포함해 2자~10자 까지 입력 가능합니다.")
        private String nickname;

        @NotBlank(message = "비밀번호를 필수 입력 항목입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,15}$",
                message = "비밀번호는 영문자, 숫자, 특수기호를 포함해 최소 10자, 최대 15자 까지 입력 가능합니다.")
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class patch {
        private Long Id;
        @Pattern(regexp = "^(\\d){4}+-(\\d){2}+-(\\d){2}$",
                message = "생년월일은 8자리를 입력해 주세요")
        private String age;
        @Pattern(regexp = "[WM]")
        private String gender;

        @Range(min = 0, max = 1000)
        private Integer height;

        @Range(min = 0, max = 1000)
        private Integer weight;
        //최초 로그인하는 사용자인지 판별하기 위한 필드값
        private Boolean initialLogin;

        public void setId(long Id) {
            this.Id = Id;
        }
    }
}

