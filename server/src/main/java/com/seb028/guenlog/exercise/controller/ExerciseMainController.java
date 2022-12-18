package com.seb028.guenlog.exercise.controller;

import com.seb028.guenlog.exercise.dto.CalendarResponseDto;
import com.seb028.guenlog.exercise.dto.ExerciseMainResponseDto;
import com.seb028.guenlog.exercise.entity.Record;
import com.seb028.guenlog.exercise.mapper.ExerciseMainMapper;
import com.seb028.guenlog.exercise.service.ExerciseMainService;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.response.MultiResponseDto;
import com.seb028.guenlog.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exercises")
@Slf4j
public class ExerciseMainController {

    private final ExerciseMainService exerciseMainService;

    private final ExerciseMainMapper exerciseMainMapper;

    private  final MemberService memberService;

    // 달력 보여주는 기능
    @GetMapping("/calendar")
    public ResponseEntity getCalendar(@RequestParam("date") String date, HttpServletRequest request) {
        Long id = memberService.findMemberId(request);

        List<ExerciseMainResponseDto.CalendarDto> calendars = exerciseMainService.findCalendar(date,id);


        return new ResponseEntity<>(new MultiResponseDto<>(calendars),
                HttpStatus.OK);
    }

    // 달력에 선택한 날짜의 세부 운동내역 출력
    @GetMapping("/calendar/detail/{today-id}")
    public ResponseEntity getDetailCanlendar(@PathVariable("today-id") @Positive @NotNull long todayId){
        Integer timer = exerciseMainService.findTimer(todayId);

        List<Record>  records =  exerciseMainService.findExercise(todayId);
        List<ExerciseMainResponseDto.ExerciseDto> exerciseDtos = exerciseMainMapper.recordToExerciseDto(records);

        return new ResponseEntity<>(new SingleResponseDto<>(exerciseMainMapper.exercisesDtoToRecordDetailDto(exerciseDtos,timer)),
                HttpStatus.OK);
    }
}
