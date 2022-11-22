package com.seb028.guenlog.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDto<T> {

    private boolean success;

    private T data;

    public SingleResponseDto(T data) {
        this.success = true;
        this.data = data;
    }

}
