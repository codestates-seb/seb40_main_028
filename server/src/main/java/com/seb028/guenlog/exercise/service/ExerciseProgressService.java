package com.seb028.guenlog.exercise.service;

import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.repository.RecordRepository;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import com.seb028.guenlog.exercise.util.ExerciseProgress;
import com.seb028.guenlog.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExerciseProgressService {

    private final ExerciseMainService exerciseMainService;
    private final RecordRepository recordRepository;
    private final TodayRepository todayRepository;
    private final MemberService memberService;


    public ExerciseProgressService(RecordRepository recordRepository,
                                   TodayRepository todayRepository,
                                   ExerciseMainService exerciseMainService,
                                   MemberService memberService) {
        this.recordRepository = recordRepository;
        this.todayRepository = todayRepository;
        this.exerciseMainService = exerciseMainService;
        this.memberService = memberService;
    }

    /**
     * 오늘 하루 운동 내역, 운동 기록 목록들 조회
     * @param date : 오늘 날짜에 대한 LocalDate 객체. yyy-MM-dd 형식.(ex. 2022-11-25)
     * @param memberId : 사용자 ID
     * @return ExerciseProgress 객체 - 오늘 하루 운동 내역, 운동 기록 목록 저장
     */
    @Transactional(readOnly = true)
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

    public ExerciseProgress updateExerciseProgress(long todayId, ExerciseProgress exerciseProgress) {
        // --- 오늘 하루 운동 내역 수정 ---
        Today today = exerciseProgress.getToday();                      // 수정을 원하는 오늘 하루 운동 내역
        Today findToday = exerciseMainService.findVerifiedToday(todayId);      // exerciseMainservice에서 Repository에 저장된 유효한 Today객체 조회
        findToday.updateTotalTime(today.getTotalTime());                // 오늘 하루 운동 내역 총 운동 시간  수정
        Today updatedToday = todayRepository.save(findToday);           // 수정된 오늘 하루 운동 내역 저장

        // --- 오늘 하루 운동 기록 목록 수정 ---
        List<Record> records = exerciseProgress.getRecords();           // 수정을 원하는 오늘 하루 운동 목록 리스트
        List<Record> findRecords = recordRepository.findByTodayId(todayId);       // RecordRepository에 저장된 운동 기록 목록 조회ㅣ

        // 수정 원하는 운동 기록 목록 길이와 저장된 운동 기록 목록 길이가 다르면 예외 발생
        if (records.size() != findRecords.size()){
            throw new BusinessLogicException(ExceptionCode.EXERCISE_NOT_MATCH);
        }

        // 운동 기록 목록들 완료 여부와 각 세트 수정
        for (int i = 0; i < findRecords.size(); i++) {
            findRecords.get(i).setIsCompleted(records.get(i).getIsCompleted());
            findRecords.get(i).setEachRecords(records.get(i).getEachRecords());
        }

        // 수정된 오늘 하루 운동 기록 목록 저장
        List<Record> updatedRecords = findRecords.stream()
                .map(record -> {
                        Record updatedRecord = recordRepository.save(record);
                        return updatedRecord;
                }).collect(Collectors.toList());

        // 수정된 오늘 하루 운동 내역, 기록 목록 ExerciseProgress 객체로 변환 후 반환
        return ExerciseProgress.builder()
                .today(updatedToday)
                .records(updatedRecords)
                .build();
    }
}
