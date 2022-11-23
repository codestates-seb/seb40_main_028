package com.seb028.guenlog.exercise.dto;

import java.sql.Date;

public interface CalendarResponseDto {

     Long getTodayId();

     Date getDueDate();

     Integer getCompleted();


}
