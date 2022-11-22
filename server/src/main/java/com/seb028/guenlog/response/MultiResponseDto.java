package com.seb028.guenlog.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponseDto<T> {

    private boolean success;
    private List<T> data;

    public MultiResponseDto(List<T> data) {
        this.success = true;
        this.data = data;
    }
}