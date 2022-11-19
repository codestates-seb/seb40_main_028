package com.seb028.guenlog.member.service;

import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.repository.MemberRepository;
import com.seb028.guenlog.member.repository.MemberWeightRepository;
import com.seb028.guenlog.member.util.MyPageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MemberWeightRepository memberWeightRepository;

    private final MemberService memberService;

    private final MemberWeightService memberWeightService;

    public MyPageService(MemberRepository memberRepository,
                         MemberWeightRepository memberWeightRepository,
                         MemberService memberService,
                         MemberWeightService memberWeightService){
        this.memberRepository = memberRepository;
        this.memberWeightRepository = memberWeightRepository;
        this.memberService = memberService;
        this.memberWeightService = memberWeightService;
    }

    public MyPageInfo getMyPageInfo(long memberId){
        // memberID를 이용해 memberService에서 사용자 개인 정보 가져옴
        Member findMember = memberService.findVerified(memberId);

        // memberId를 이용해 memberWeightService에서 사용자의 가장 최신 몸무게 값 가져옴.
        MemberWeight findMemberWeight = memberWeightService.findRecentOneWeight(memberId);

        // member, memberWeight 객체를 이용하여 MyPageInfo 객체 생성후 반환
        MyPageInfo findMyPageInfo = MyPageInfo.builder()
                .member(findMember)
                .memberWeight(findMemberWeight)
                .build();

        return findMyPageInfo;
    }
}
