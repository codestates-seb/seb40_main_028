package com.seb028.guenlog.exercise.controller;

import com.seb028.guenlog.exercise.dto.ExerciseProgressPatchDto;
import com.seb028.guenlog.exercise.mapper.ExerciseProgressMapper;
import com.seb028.guenlog.exercise.service.ExerciseProgressService;
import com.seb028.guenlog.exercise.util.ExerciseProgress;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.response.SingleResponseDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@RestController
@RequestMapping("/exercises/progress")
public class ExerciseProgressController {

    private final ExerciseProgressService exerciseProgressService;
    private final MemberService memberService;

    private final ExerciseProgressMapper mapper;


    public ExerciseProgressController(ExerciseProgressMapper mapper,
                                      ExerciseProgressService exerciseProgressService,
                                      MemberService memberService) {
        this.mapper = mapper;
        this.exerciseProgressService = exerciseProgressService;
        this.memberService = memberService;
    }

    /**
     * 오늘 하루 운동 진행하기 위해 하루 운동 내역, 운동 기록목록들 조회
     * @param date : 오늘 날짜에 대한 LocalDate 객체. yyy-MM-dd 형식.(ex. 2022-11-25)
     * @param request : 클라이언트 요청(헤더에 토큰 포함)
     * @return 하루 운동 내역, 운동 기록 목록을 담을 ResponseDto의 ResponseEntity, HttpStatus OK
     */
    @GetMapping
    public ResponseEntity getExerciseProgress(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                              HttpServletRequest request) {
        // Http request의 JWT로부터 사용자 아이디 추출
        long memberId = memberService.findMemberId(request);

        // 사용자의 ID와 날짜 객체를 이용하여 하루 운동 내역, 운동 기록 목록들을 ExerciesePrgoress 객체에 저장해서 조회
        ExerciseProgress exerciseProgress = exerciseProgressService.findTodayExerciseProgress(date, memberId);

        // ExerciseProgress 객체를 ResponseDto로 변환 후 반환
        return new ResponseEntity<> (
                new SingleResponseDto<>(mapper.exerciseProgressToExerciseProgressResponseDto(exerciseProgress)),
                HttpStatus.OK
        );
    }

    /**
     * 오늘 하루 운동 진행 후 운동 완료
     * @param todayId : 오늘 하루 운동 ID
     * @param exerciseProgressPatchDto : 운동시간, 운동 목록들의 완료여부가 담긴 Patch DTO
     * @param request  : 클라이언트 요청(헤더에 토큰 포함)
     * @return ResponseEntity 리턴
     */
    @PatchMapping("/{today-id}")
    public ResponseEntity patchExerciseProgress (   @PathVariable("today-id") @Positive @NotNull long todayId,
                                                    @RequestBody ExerciseProgressPatchDto exerciseProgressPatchDto,
                                                    HttpServletRequest request) {
        // Http request의 JWT로부터 사용자 아이디 추출
        long memberId = memberService.findMemberId(request);

        // PatchDTO를 ExerciseProgress 객체로 변환 후 ExerciseProgress Service에서 수정 후 반환
        ExerciseProgress exerciseProgress =
                exerciseProgressService.updateExerciseProgress(todayId,
                        mapper.exerciseProgressPatchDtoToExerciseProgress(exerciseProgressPatchDto));

        // 추가적인 객체 반환없이 ResponseEntity로 HTTP Status OK 반환
        return new ResponseEntity<> (
                new SingleResponseDto<>(null),
                HttpStatus.OK
        );
    }
}
