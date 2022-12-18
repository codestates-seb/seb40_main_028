package com.seb028.guenlog.exercise.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.exercise.dto.CalendarResponseDto;
import com.seb028.guenlog.exercise.dto.ExerciseMainResponseDto;
import com.seb028.guenlog.exercise.entity.QRecord;
import com.seb028.guenlog.exercise.entity.QToday;
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

    private final JPAQueryFactory jpaQueryFactory;

    public ExerciseMainService(TodayRepository todayRepository, RecordRepository recordRepository, JPAQueryFactory jpaQueryFactory) {
        this.todayRepository = todayRepository;
        this.recordRepository = recordRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ExerciseMainResponseDto.CalendarDto> findCalendar(String date, Long id){

        //Native Query를 이용한 처리
       // List<CalendarResponseDto> findCalendar= todayRepository.findByLikeDateAndMemberId(date, id);

        //Query Dsl을 이용한 처리
        QRecord qRecord = QRecord.record;
        QToday qToday = QToday.today;

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qToday.dueDate
                , ConstantImpl.create("%Y-%m"));

        List<ExerciseMainResponseDto.CalendarDto> findCalendars= jpaQueryFactory
                .select(Projections.bean(ExerciseMainResponseDto.CalendarDto.class,qToday.id.as("todayId"), qToday.dueDate.as("dueDate"),
                        ( new CaseBuilder().when((qRecord.isCompleted.when(true).then(1).otherwise(0)).sum().goe(1)).then(1).otherwise(0)).as("completed")))
                .from(qToday)
                .leftJoin(qRecord)
                .on(qToday.id.eq(qRecord.today.id))
                .where(qToday.member.id.eq(id).and(formattedDate.eq(date)))
                .groupBy(qToday.id)
                .orderBy(qToday.dueDate.asc())
                .fetch();


        return findCalendars;
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
