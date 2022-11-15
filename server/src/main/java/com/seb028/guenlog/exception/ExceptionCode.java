package com.seb028.guenlog.exception;

import lombok.Getter;

public enum ExceptionCode {
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(413,"Question not found"),
    USER_NOT_FOUND(415, "User not found");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
