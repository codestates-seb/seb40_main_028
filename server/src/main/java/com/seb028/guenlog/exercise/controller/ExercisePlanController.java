package com.seb028.guenlog.exercise.controller;


import com.seb028.guenlog.exercise.dto.ExercisePlanRequestDto;
import com.seb028.guenlog.exercise.entity.Category;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.mapper.ExercisePlanMapper;
import com.seb028.guenlog.exercise.repository.ExerciseRepository;
import com.seb028.guenlog.exercise.repository.RecordRepository;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import com.seb028.guenlog.exercise.service.ExercisePlanService;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.response.MultiResponseDto;
import com.seb028.guenlog.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exercises")
@Slf4j
public class ExercisePlanController {

    private final ExercisePlanService exercisePlanService;

    private final ExercisePlanMapper exercisePlanMapper;

    private final ExerciseRepository exerciseRepository;

    private final RecordRepository recordRepository;
    private final TodayRepository todayRepository;

    private final MemberService memberService;


    // 운동 카테고리, 운동 모달창 조회
    @GetMapping("/category")
    public ResponseEntity getCategory() {

        List<Category> category = exercisePlanService.findCategory();


        return new ResponseEntity<>(new MultiResponseDto<>(category),
                HttpStatus.OK);
    }



    // 운동 베이스 페이지 조회
    @GetMapping("/records")
    public ResponseEntity getTodos(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date, HttpServletRequest request) {
        Long id = memberService.findMemberId(request);
        List<Record> record = exercisePlanService.findTodo(date,id);

        System.out.println(record);
        
        // 날짜에 운동이 있는지 확인하기 위한 널처리 응답
        Object result;
        if(record == null){
            result = new SingleResponseDto<>(record);
        }else{
            result = new MultiResponseDto<>(exercisePlanMapper.recordToRecordBaseResponseDto(record));
        }
        
        
        return new ResponseEntity<>(result,
                    HttpStatus.OK);
    }

    // 운동 상세 모달창 조회
    @GetMapping("/records/{record-id}")
    public ResponseEntity getRecords(@PathVariable("record-id") @Positive @NotNull long recordId) {

        Record record = exercisePlanService.findDetail(recordId);


        return new ResponseEntity<>(new SingleResponseDto<>(exercisePlanMapper.recordToRecordDetailNameResponseDto(record)),
                HttpStatus.OK);
    }


    //베이스 페이지 운동 등록
    @PostMapping("/records")
    public ResponseEntity postRecord(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date, @RequestBody @Valid ExercisePlanRequestDto.TodoPostDto todoPostDto, HttpServletRequest request){
        Long id = memberService.findMemberId(request);
        Record record = exercisePlanService.createRecord(date,id,todoPostDto);

        return new ResponseEntity<>(new SingleResponseDto<>(exercisePlanMapper.recordToRecordPostResponseDto(record)),
                HttpStatus.CREATED);
    }

    //베이스 페이지 운동 수정
    @PatchMapping("/records/{record-id}")
    public ResponseEntity postRecord(@PathVariable("record-id") @Positive @NotNull long recordId,@RequestBody @Valid ExercisePlanRequestDto.TodoPostDto todoPostDto){

        Record record = exercisePlanService.updateRecord(recordId,todoPostDto);

        return new ResponseEntity<>(new SingleResponseDto<>(exercisePlanMapper.recordToRecordPostResponseDto(record)),
                HttpStatus.OK);
    }

    //베이스 페이지 운동 삭제
    @DeleteMapping("/records/{record-id}")
    public ResponseEntity deleteTodo(@PathVariable("record-id") @Positive @NotNull long id){
        exercisePlanService.deleteTodo(id);
        return new ResponseEntity<>(new SingleResponseDto<>("삭제완료"),
                HttpStatus.NO_CONTENT);
    }





}
