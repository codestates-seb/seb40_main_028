package com.seb028.guenlog.member.util;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
public class MonthlyWeight {
    private Date dates;
    private double weight;


    public void setWeight(double weight) {
        this.weight = weight;
    }
}
