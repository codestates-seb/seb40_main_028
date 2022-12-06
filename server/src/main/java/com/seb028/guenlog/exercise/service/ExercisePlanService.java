package com.seb028.guenlog.exercise.service;

import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.exercise.dto.ExercisePlanRequestDto;
import com.seb028.guenlog.exercise.entity.Category;
import com.seb028.guenlog.exercise.entity.Exercise;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.repository.CategoryRepository;
import com.seb028.guenlog.exercise.repository.ExerciseRepository;
import com.seb028.guenlog.exercise.repository.RecordRepository;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExercisePlanService {
    private final MemberRepository memberRepository;


    private final ExerciseRepository exerciseRepository;

    private final CategoryRepository categoryRepository;

    private final RecordRepository recordRepository;


    private final TodayRepository todayRepository;

    public ExercisePlanService(MemberRepository memberRepository, ExerciseRepository exerciseRepository, CategoryRepository categoryRepository, RecordRepository recordRepository, TodayRepository todayRepository) {
        this.memberRepository = memberRepository;
        this.exerciseRepository = exerciseRepository;
        this.categoryRepository = categoryRepository;
        this.recordRepository = recordRepository;
        this.todayRepository = todayRepository;
    }

    @Transactional
    public Record   createRecord(LocalDate date, Long id, ExercisePlanRequestDto.TodoPostDto post){

        Today todays = new Today();
        //기존에 today에 동일한 날짜와 유저의 일치하는 id 가 있으면 넘김
        Optional<Today> optionalToday = Optional.ofNullable(todayRepository.findByDateAndMemberId(date,id));
        if(optionalToday.isEmpty()){
            Member member = new Member();
            member.setId(id);

            Today today = Today.builder()
                    .member(member)
                    .dueDate(date)
                    .totalTime(0)
                    .build();
            todays =   todayRepository.save(today);
        }else{
            todays = optionalToday.orElseThrow();
        }

        //운동 id 체크
        findVerifiedExercise(post.getExerciseId());


        // record 테이블에 파일 저장
        Exercise ex = Exercise.builder()
                .id(post.getExerciseId())
                .build();


        Record record = Record.builder()
                .today(todays)
                .exercise(ex)
                .eachRecords(post.getEachRecords())
                .isCompleted(false)
                .build();

        recordRepository.save(record);


        return record;
    }


    @Transactional
    public Record updateRecord(Long id, ExercisePlanRequestDto.TodoPostDto post){
        Record findRecord = findVerifiedRecode(id);

        findRecord.setEachRecords(post.getEachRecords());


        recordRepository.save(findRecord);

        return findRecord;
    }


    public List<Category> findCategory(){

        return categoryRepository.findAll();
    }

    @Transactional
    public List<Record> findTodo(LocalDate date, Long id){

        Today findToday = todayRepository.findByDateAndMemberId(date, id);
        if(findToday == null){
            return  null;
        }

        List<Record> records =   recordRepository.findByTodayId(findToday.getId());

        return records;
    }

    public Record findDetail(Long id){
        Record findRecord = findVerifiedRecode(id);
        return findRecord;
    }

    @Transactional
    public void deleteTodo(Long id){
        Record findRecord = findVerifiedRecode(id);

        recordRepository.delete(findRecord);
    }


    public Record findVerifiedRecode(Long id){ //요청된 질문이 DB에 없으면 에러
        Optional<Record> optionalRecord = recordRepository.findById(id);
        Record findRecord = optionalRecord.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.RECORD_NOT_FOUND));
        return findRecord;
    }


    public Exercise findVerifiedExercise(Long id){ //요청된 질문이 DB에 없으면 에러
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        Exercise findExercise= optionalExercise.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.EXERCISE_NOT_FOUND));
        return findExercise;
    }

}
