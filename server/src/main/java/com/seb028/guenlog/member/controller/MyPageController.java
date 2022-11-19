package com.seb028.guenlog.member.controller;

import com.seb028.guenlog.member.dto.MyPageInfoDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.mapper.MyPageInfoMapper;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.member.service.MemberWeightService;
import com.seb028.guenlog.member.service.MyPageService;
import com.seb028.guenlog.member.util.MyPageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users/mypages")
@Validated
public class MyPageController {

    private final MemberService memberService;

    private final MyPageService myPageService;

    private final MemberWeightService memberWeightService;

    private final MyPageInfoMapper myPageInfoMapper;

    public MyPageController(MemberService memberService,
                            MyPageService myPageService,
                            MemberWeightService memberWeightService,
                            MyPageInfoMapper myPageInfoMapper) {
        this.memberService = memberService;
        this.myPageService = myPageService;
        this.memberWeightService = memberWeightService;
        this.myPageInfoMapper = myPageInfoMapper;
    }

    @GetMapping("/info")
    public ResponseEntity getMyPageInfo(
            // TODO: HttpServeletRequest request - 토큰
    ) {
        // TODO: http request로부터 사용자의 memberId 추출
        // long memberId = memberService.findMember();
        long memberId = 1L;

        // memberId를 통해 myPageService에서 MyPageInfo 객체 반환
        MyPageInfo myPageInfo = myPageService.getMyPageInfo(memberId);

        // 사용자의 개인정보에 대한 MyPageInfo 객체를 ResponsDTO로 변환후 반환
        return new ResponseEntity<> (
                myPageInfoMapper.myPageInfoToMyPageInfoResponseDto(myPageInfo),
                HttpStatus.OK
        );
    }
}
