package com.seb028.guenlog.member.mapper;

import com.seb028.guenlog.member.dto.MyPageResponseDto;
import com.seb028.guenlog.member.util.MyPage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyPageMapper {
    /**
     * 마이페이지와 관련된 MyPage객체를 MyPageResponseDto 객체로 변환
     * @param myPage - 마이페이지와 관련된 총 달별 운동 기록, 총 달별 몸무게 평균 기록을 저장한 MyPage 객체 
     * @return 총 달별 운동 기록, 총 달별 몸무게 평균 기록을 저장한 MyPage 객체에 대한 ResponseDto 객체 
     */
    default MyPageResponseDto myPageToMyPageResponseDto(MyPage myPage) {
        // 총 달별 운동 횟수 DTO 객체 리스트 생성
        List<MyPageResponseDto.MonthlyRecordDto> monthlyRecords = myPage.getMonthlyRecords().stream()
                .map(monthlyRecord -> {
                    String date = monthlyRecord.getDates();                 // 한 달 날짜
                    Integer record = monthlyRecord.getRecord();             // 한 달 총 운동 횟수 
                    return MyPageResponseDto.MonthlyRecordDto.builder()     // Dto로 변환 
                            .date(date)
                            .record(record)
                            .build();
                }).collect(Collectors.toList());

        // 총 달별 몸무게 평균 기록 DTO 객체 리스트 생성 
        List<MyPageResponseDto.MonthlyWeightDto> monthlyWeights = myPage.getMonthlyWeights().stream()
                .map(monthlyWeight -> {
                    String date = monthlyWeight.getDates();                 // 한 달 날짜 
                    Double weight = monthlyWeight.getWeight();              // 한달 몸무게 평균
                    return MyPageResponseDto.MonthlyWeightDto.builder()     // DTO로 변환 
                            .date(date)
                            .weight(weight)
                            .build();
                }).collect(Collectors.toList());

        // 총 달별 운동 횟수, 총 달별 몸무게 평균 기록을 저장한 MyPageResponseDto 객체 생성 후 반환
        return MyPageResponseDto.builder()
                .monthlyRecords(monthlyRecords)
                .monthlyWeights(monthlyWeights)
                .build();
    }
}
