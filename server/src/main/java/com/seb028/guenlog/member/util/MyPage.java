package com.seb028.guenlog.member.util;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyPage {
    private List<MonthlyRecord> monthlyRecords;
    private List<MonthlyWeight> monthlyWeights;

    public void addMonthlyRecord(MonthlyRecord monthlyRecord) {
        monthlyRecords.add(monthlyRecord);
    }

    public void addMonthlyWeight(MonthlyWeight monthlyWeight) {
        monthlyWeights.add(monthlyWeight);
    }
}
