package com.seb028.guenlog.member.controller;

import com.seb028.guenlog.member.dto.MyPageInfoDto;
import com.seb028.guenlog.member.dto.PasswordPatchDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.mapper.MyPageInfoMapper;
import com.seb028.guenlog.member.mapper.MyPageMapper;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.member.service.MemberWeightService;
import com.seb028.guenlog.member.service.MyPageService;
import com.seb028.guenlog.member.util.MyPage;
import com.seb028.guenlog.member.util.MyPageInfo;
import com.seb028.guenlog.response.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/users/mypages")
@Validated
public class MyPageController {

    private final MemberService memberService;

    private final MyPageService myPageService;

    private final MemberWeightService memberWeightService;

    private final MyPageInfoMapper myPageInfoMapper;

    private final MyPageMapper myPageMapper;

    public MyPageController(MemberService memberService,
                            MyPageService myPageService,
                            MemberWeightService memberWeightService,
                            MyPageInfoMapper myPageInfoMapper,
                            MyPageMapper myPageMapper) {
        this.memberService = memberService;
        this.myPageService = myPageService;
        this.memberWeightService = memberWeightService;
        this.myPageInfoMapper = myPageInfoMapper;
        this.myPageMapper = myPageMapper;
    }

    @GetMapping("/info")
    public ResponseEntity getMyPageInfo( HttpServletRequest request) {
        // http request로부터 사용자의 memberId 추출
        long memberId = memberService.findMemberId(request);

        // memberId를 통해 myPageService에서 MyPageInfo 객체 반환
        MyPageInfo myPageInfo = myPageService.getMyPageInfo(memberId);

        // 사용자의 개인정보에 대한 MyPageInfo 객체를 ResponsDTO로 변환후 반환
        return new ResponseEntity<> (
                new SingleResponseDto<>(myPageInfoMapper.myPageInfoToMyPageInfoResponseDto(myPageInfo)),
                HttpStatus.OK
        );
    }

    @PatchMapping("/info")
    public ResponseEntity patchMyPageInfo(
            HttpServletRequest request,
            @RequestBody @Valid MyPageInfoDto.Patch myPageInfoPatchDto) {
        // http request로부터 사용자의 memberId 추출
       long memberId = memberService.findMemberId(request);

        // MyPageInfoDto를 MyPageInfo 객체로 변환
        MyPageInfo myPageInfo = myPageInfoMapper.myPageInfoPatchDtoToMyPageInfo(myPageInfoPatchDto);

        // MypageInfo객체의 Member객체에 사용자 ID 부여
        myPageInfo.getMember().setId(memberId);

        // MyPageService에서 MyPageInfo 객체 전달하여 개인정보 수정
        MyPageInfo updatedMyPageInfo =
                myPageService.updateMyPageInfo(myPageInfo);

        // 수정 완료를 MyPageInfoResponseDTO와 함께 HttpStatus OK로 리턴
        return new ResponseEntity<>(
                new SingleResponseDto<>(myPageInfoMapper.myPageInfoToMyPageInfoResponseDto(updatedMyPageInfo)),
                HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity patchPassword(HttpServletRequest request,
                                        @RequestBody @Valid PasswordPatchDto passwordPatchDto) {

        // http request로부터 사용자의 memberId 추출
        long memberId = memberService.findMemberId(request);

        // mypageService에서 비밀번호 수정
        myPageService.updatePassword(passwordPatchDto, memberId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(null),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getMyPage(HttpServletRequest request) {
        // http request로부터 사용자의 memberId 추출
        long memberId = memberService.findMemberId(request);

        // memberId를 이용해 MyPageService에서 getMyPage메서드 호출
        MyPage myPage = myPageService.findMyPage(memberId);

        return new ResponseEntity<> (
                new SingleResponseDto<>(myPageMapper.myPageToMyPageResponseDto(myPage)),
                HttpStatus.OK);
    }
}
