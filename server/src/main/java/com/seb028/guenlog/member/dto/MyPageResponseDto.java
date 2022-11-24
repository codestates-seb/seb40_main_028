package com.seb028.guenlog.member.dto;

import com.seb028.guenlog.member.util.MonthlyRecord;
import com.seb028.guenlog.member.util.MonthlyWeight;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyPageResponseDto {
    private List<MonthlyRecord> monthlyRecords;
    private List<MonthlyWeight> monthlyWeights;
}
