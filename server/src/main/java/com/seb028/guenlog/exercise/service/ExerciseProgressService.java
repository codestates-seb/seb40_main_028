package com.seb028.guenlog.exercise.service;

import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.repository.RecordRepository;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import com.seb028.guenlog.exercise.util.ExerciseProgress;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExerciseProgressService {

    private final ExerciseMainService exerciseMainService;
    private final RecordRepository recordRepository;
    private final TodayRepository todayRepository;


    public ExerciseProgressService(RecordRepository recordRepository,
                                   TodayRepository todayRepository,
                                   ExerciseMainService exerciseMainService) {
        this.recordRepository = recordRepository;
        this.todayRepository = todayRepository;
        this.exerciseMainService = exerciseMainService;
    }

    /**
     * 오늘 하루 운동 내역, 운동 기록 목록들 조회
     * @param date : 오늘 날짜에 대한 LocalDate 객체. yyy-MM-dd 형식.(ex. 2022-11-25)
     * @param memberId : 사용자 ID
     * @return ExerciseProgress 객체 - 오늘 하루 운동 내역, 운동 기록 목록 저장
     */
    public ExerciseProgress findTodayExerciseProgress(LocalDate date, long memberId) {
        // 오늘 하루 운동 내역 조회
        Today findToday = todayRepository.findByDateAndMemberId(date, memberId);
        if (findToday == null) {    // 오늘 하루 운동이 없으면 예외 처리
            throw new BusinessLogicException(ExceptionCode.TODAY_NOT_FOUND);
        }

        // 오늘 하루 운동 기록 목록 조회
        List<Record> findRecords = exerciseMainService.findExercise(findToday.getId());
        if (findRecords.isEmpty()) {    // 오늘 하루 운동 기록들이 없으면 예외 처리
            throw new BusinessLogicException(ExceptionCode.RECORD_NOT_FOUND);
        }

        // 오늘 하루 운동 내역, 운동 기록 목록 반환
        return ExerciseProgress.builder()
                .today(findToday)
                .records(findRecords)
                .build();
    }
}
