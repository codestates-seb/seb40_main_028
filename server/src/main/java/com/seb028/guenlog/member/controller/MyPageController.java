package com.seb028.guenlog.member.controller;

import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.mapper.MyPageInfoMapper;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.member.service.MemberWeightService;
import com.seb028.guenlog.member.service.MyPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users/mypages")
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

        // memberID를 이용해 memberService에서 사용자 개인 정보 가져옴
        Member member = memberService.findVerified(memberId);
        // memberId를 이용해 사용자의 가장 최신 몸무게 값 가져옴.
        MemberWeight memberWeight = memberWeightService.findRecentOneWeight(memberId);

        // 사용자 개인정보, 가장 최신 몸무게 값을 ResponsDTO로 변환후 반환
        return new ResponseEntity<> (
                myPageInfoMapper.myPageInfoToMyPageInfoResponseDto(member, memberWeight),
                HttpStatus.OK
        );
    }
}
