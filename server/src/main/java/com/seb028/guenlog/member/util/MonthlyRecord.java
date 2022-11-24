package com.seb028.guenlog.member.util;

import lombok.Builder;
import lombok.Getter;

import java.sql.Date;

@Getter
@Builder
public class MonthlyRecord {
    private Date date;
    private int record;

    public void setRecord(int record) {
        this.record = record;
    }
}
