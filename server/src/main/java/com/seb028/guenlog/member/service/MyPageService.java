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

    public MyPageInfo updateMyPageInfo(MyPageInfo myPageInfo) {
        // MyPageInfo객체로부터 Member 객체 추출
        Member member = myPageInfo.getMember();

        // 사용자ID를 통해 MemberService에서 유효한 사용자인지 확인
        Member findMember = memberService.findVerified(member.getId());

        // 사용자 닉네임 변경
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickName -> findMember.setNickname(nickName));
        // 사용자 나이 변경
        Optional.ofNullable(member.getAge())
                .ifPresent(age -> findMember.setAge(age));
        // 사용자 성별 변경
        Optional.ofNullable(member.getGender())
                .ifPresent(gender -> findMember.setGender(gender));
        // 사용자 키 변경
        Optional.ofNullable(member.getHeight())
                .ifPresent(height -> findMember.setHeight(height));

        // 변경된 사용자 정보 저장
        Member updatedMember = memberRepository.save(findMember);

        // TODO: 몸무게 저장 관련해서 리팩터링 필요
        // MyPageInfo객체로부터 MemberWeight 객체 추출
        MemberWeight memberWeight = myPageInfo.getMemberWeight();

        // memberWeight 객체에 변경된 사용자 정보 저장
        memberWeight.setMember(updatedMember);

        // 몸무게에 수정된 값이 존재하면 사용자 몸무게 변경
        Optional.ofNullable(memberWeight.getWeight())
                .ifPresent(weight -> memberWeightService.createMemberWeight(memberWeight));

        // 가장 최근에 저장된 몸무게 조회
        MemberWeight updatedMemberWeight = memberWeightService.findRecentOneWeight(updatedMember.getId());

        // 수정된 사용자와 몸무게를  MyPageInfo 객체로 변환 후 반환
        MyPageInfo updatedMyPageInfo = MyPageInfo.builder()
                .member(updatedMember)
                .memberWeight(updatedMemberWeight)
                .build();

        return updatedMyPageInfo;
    }

    public void updatePassword(PasswordPatchDto passwordPatchDto, long memberId) {
        // memberId를 통해 member객체 조회
        Member findMember = memberService.findVerified(memberId);

        String password = passwordPatchDto.getPassword();          // 입력한 비밀 번호
        // 입력한 비밀번호와 저장된 비밀번호 비교 후 다르면
        if (!passwordEncoder.matches(password, findMember.getPassword()))
            // 에러 출력
            throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_MATCH);

        // 새로운 비밀 번호 가져와서 암호화하여 저장
        String newPassword = passwordPatchDto.getNewPassword();
        findMember.setPassword(passwordEncoder.encode(newPassword));

        memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public MyPage findMyPage(long memberId) {
        // memberId를 통해 member객체 조회
        Member findMember = memberService.findVerified(memberId);

        // 사용자의 한달 총 운동 기록 최근 6개월 단위로 조회
        List<MonthlyRecord> monthlyRecords = todayRepository.findRecentSixMonthsRecordByMemberId(memberId);
        // 사용자의 한달 몸무게 평균 최근 6개월 단위로 조회
        List<MonthlyWeight> monthlyWeights = memberWeightRepository.findRecentSixMonthWeightByMemberId(memberId);

        // 운동 기록 합과 몸무게 평균을 MyPage 객체로 변환 후 반환
        return MyPage.builder()
                .monthlyRecords(monthlyRecords)
                .monthlyWeights(monthlyWeights)
                .build();
    }
}
