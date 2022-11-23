package com.seb028.guenlog.exercise.service;

import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.exercise.dto.CalendarResponseDto;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.repository.RecordRepository;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExerciseMainService {


    private final TodayRepository todayRepository;

    private final RecordRepository recordRepository;

    public ExerciseMainService(TodayRepository todayRepository, RecordRepository recordRepository) {
        this.todayRepository = todayRepository;
        this.recordRepository = recordRepository;
    }

    public List<CalendarResponseDto> findCalendar(String date, Long id){
        List<CalendarResponseDto> findCalendar= todayRepository.findByLikeDateAndMemberId(date, id);

        return findCalendar;
    }

    public List<Record> findExercise(Long id){
        List<Record> findExercise= recordRepository.findByTodayId(id);

        return findExercise;
    }

    public Integer findTimer(Long id){
      Integer timer =   findVerifiedToday(id).getTotalTime();
      return timer;
    }



    public Today findVerifiedToday(Long id){
        Optional<Today> optionalToday = todayRepository.findById(id);
        Today findToday= optionalToday.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.TODAY_NOT_FOUND));
        return findToday;
    }



}
