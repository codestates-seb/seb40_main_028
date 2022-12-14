package com.seb028.guenlog.member.service;

import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.exercise.entity.Today;
import com.seb028.guenlog.exercise.repository.TodayRepository;
import com.seb028.guenlog.member.dto.PasswordPatchDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.repository.MemberRepository;
import com.seb028.guenlog.member.repository.MemberWeightRepository;
import com.seb028.guenlog.member.util.MonthlyRecord;
import com.seb028.guenlog.member.util.MonthlyWeight;
import com.seb028.guenlog.member.util.MyPage;
import com.seb028.guenlog.member.util.MyPageInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MemberWeightRepository memberWeightRepository;

    private final MemberService memberService;

    private final MemberWeightService memberWeightService;

    private final PasswordEncoder passwordEncoder;

    private final TodayRepository todayRepository;

    public MyPageService(MemberRepository memberRepository,
                         MemberWeightRepository memberWeightRepository,
                         MemberService memberService,
                         MemberWeightService memberWeightService,
                         PasswordEncoder passwordEncoder,
                         TodayRepository todayRepository) {
        this.memberRepository = memberRepository;
        this.memberWeightRepository = memberWeightRepository;
        this.memberService = memberService;
        this.memberWeightService = memberWeightService;
        this.passwordEncoder = passwordEncoder;
        this.todayRepository = todayRepository;
    }

    @Transactional(readOnly = true)
    public MyPageInfo getMyPageInfo(long memberId) {
        // memberID??? ????????? memberService?????? ????????? ?????? ?????? ?????????
        Member findMember = memberService.findVerified(memberId);

        // memberId??? ????????? memberWeightService?????? ???????????? ?????? ?????? ????????? ??? ?????????.
        MemberWeight findMemberWeight = memberWeightService.findRecentOneWeight(memberId);

        // member, memberWeight ????????? ???????????? MyPageInfo ?????? ????????? ??????
        MyPageInfo findMyPageInfo = MyPageInfo.builder()
                .member(findMember)
                .memberWeight(findMemberWeight)
                .build();

        return findMyPageInfo;
    }

    public MyPageInfo updateMyPageInfo(MyPageInfo myPageInfo) {
        // MyPageInfo??????????????? Member ?????? ??????
        Member member = myPageInfo.getMember();

        // ?????????ID??? ?????? MemberService?????? ????????? ??????????????? ??????
        Member findMember = memberService.findVerified(member.getId());

        // ????????? ????????? ??????
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickName -> findMember.setNickname(nickName));
        // ????????? ?????? ??????
        Optional.ofNullable(member.getAge())
                .ifPresent(age -> findMember.setAge(age));
        // ????????? ?????? ??????
        Optional.ofNullable(member.getGender())
                .ifPresent(gender -> findMember.setGender(gender));
        // ????????? ??? ??????
        Optional.ofNullable(member.getHeight())
                .ifPresent(height -> findMember.setHeight(height));

        // ????????? ????????? ?????? ??????
        Member updatedMember = memberRepository.save(findMember);

        // TODO: ????????? ?????? ???????????? ???????????? ??????
        // MyPageInfo??????????????? MemberWeight ?????? ??????
        MemberWeight memberWeight = myPageInfo.getMemberWeight();

        // memberWeight ????????? ????????? ????????? ?????? ??????
        memberWeight.setMember(updatedMember);

        // ???????????? ????????? ?????? ???????????? ????????? ????????? ??????
        Optional.ofNullable(memberWeight.getWeight())
                .ifPresent(weight -> memberWeightService.createMemberWeight(memberWeight));

        // ?????? ????????? ????????? ????????? ??????
        MemberWeight updatedMemberWeight = memberWeightService.findRecentOneWeight(updatedMember.getId());

        // ????????? ???????????? ????????????  MyPageInfo ????????? ?????? ??? ??????
        MyPageInfo updatedMyPageInfo = MyPageInfo.builder()
                .member(updatedMember)
                .memberWeight(updatedMemberWeight)
                .build();

        return updatedMyPageInfo;
    }

    public void updatePassword(PasswordPatchDto passwordPatchDto, long memberId) {
        // memberId??? ?????? member?????? ??????
        Member findMember = memberService.findVerified(memberId);

        String password = passwordPatchDto.getPassword();          // ????????? ?????? ??????
        // ????????? ??????????????? ????????? ???????????? ?????? ??? ?????????
        if (!passwordEncoder.matches(password, findMember.getPassword()))
            // ?????? ??????
            throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_MATCH);

        // ????????? ?????? ?????? ???????????? ??????????????? ??????
        String newPassword = passwordPatchDto.getNewPassword();
        findMember.setPassword(passwordEncoder.encode(newPassword));

        memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public MyPage findMyPage(long memberId) {
        // memberId??? ?????? member?????? ??????
        Member findMember = memberService.findVerified(memberId);

        // ???????????? ?????? ??? ?????? ?????? ?????? 6?????? ????????? ??????
        List<MonthlyRecord> monthlyRecords = todayRepository.findRecentSixMonthsRecordByMemberId(memberId);
        // ???????????? ?????? ????????? ?????? ?????? 6?????? ????????? ??????
        List<MonthlyWeight> monthlyWeights = memberWeightRepository.findRecentSixMonthWeightByMemberId(memberId);

        // ?????? ?????? ?????? ????????? ????????? MyPage ????????? ?????? ??? ??????
        return MyPage.builder()
                .monthlyRecords(monthlyRecords)
                .monthlyWeights(monthlyWeights)
                .build();
    }
}
