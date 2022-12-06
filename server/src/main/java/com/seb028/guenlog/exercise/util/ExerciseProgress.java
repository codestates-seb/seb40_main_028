package com.seb028.guenlog.exercise.util;

import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExerciseProgress {         // 오늘 운동 진행에 대한 목록 객체
    Today today;                        // 오늘 하루 운동 내역
    List<Record> records;               // 오늘 하루 운동 기록 목록
}
