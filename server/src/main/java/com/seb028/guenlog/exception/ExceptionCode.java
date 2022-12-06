package com.seb028.guenlog.exception;

import lombok.Getter;

public enum ExceptionCode {
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(413,"Question not found"),
    USER_NOT_FOUND(415, "User not found"),
    USER_EMAIL_EXIST(409, "User email Exist"),
    USER_NICKNAME_EXIST(409, "User Nickname Exist"),

    PASSWORD_NOT_MATCH(401, "Password Not Match"),

    RECORD_NOT_FOUND(419, "Record not found"),

    EXERCISE_NOT_FOUND(420, "Exercise not found"),
    TODAY_NOT_FOUND(421, "Today not found"),

    EXERCISE_NOT_MATCH(422, "Exercise Not Match");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
