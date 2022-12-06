package com.seb028.guenlog.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class LoginDto {
    @NotBlank(message = "이메일은 필수 입력 항목입니다")
    @Email(message = "이메일 형식이 맞지 않습니다")
    private String username;

    @NotBlank(message = "비밀번호를 필수 입력 항목입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{10,15}$",
            message = "비밀번호는 영문자, 숫자, 특수기호를 포함해 최소 10자, 최대 15자 까지 입력 가능합니다.")
    private String password;
}
