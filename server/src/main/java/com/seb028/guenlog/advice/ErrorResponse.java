package com.seb028.guenlog.advice;


import com.seb028.guenlog.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private int status;
    private String message;

    private ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(ExceptionCode exceptionCode){
        return new ErrorResponse(exceptionCode.getStatus(),exceptionCode.getMessage());
    }
}