package com.seb028.guenlog.member.service;

import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.repository.MemberRepository;
import com.seb028.guenlog.member.repository.MemberWeightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtVerificationFilter jwtVerificationFilter;
    private final PasswordEncoder passwordEncoder;
    private final MemberWeightService memberWeightService;
    private final MemberWeightRepository memberWeightRepository;


    /**
     * @param member : email, nickname, password 입력 받아 Member 객체 생성
     * @return MemberRepository -> dataBase에 member 객체 저장
     */
    public Member createMember(Member member) {
        verifyExistEmail(member.getEmail());
        verifyExistNickname(member.getNickname());
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member);
    }

    public Member initialMember(Member member, Integer infoWeight) {
        Member initialMember = findVerified(member.getId());
        //성별 저장
        Optional.ofNullable(member.getGender())
                .ifPresent(gender -> initialMember.setGender(gender));
        //나이 저장
        Optional.ofNullable(member.getAge())
                .ifPresent(age -> initialMember.setAge(age));
        //키 저장
        Optional.ofNullable(member.getHeight())
                .ifPresent(height -> initialMember.setHeight(height));

        //MemberWeight 객체 생성
        MemberWeight memberWeight = new MemberWeight();
        //몸무게 테이블 생성
        Optional.ofNullable(memberWeight.getWeight())
                .ifPresent(weight-> memberWeightService.createMemberWeight(memberWeight));
        //MemberWeight 객체에 사용자 몸무게, 추가 입력 사항 저장
        MemberWeight newWeight = MemberWeight.builder()
                .weight(infoWeight)
                .member(initialMember)
                .build();
        memberWeightRepository.save(newWeight);

        //최초 로그인 정보입력 완료
        initialMember.setInitialLogin(true);
        //Repository에 추가 입력 사항 저장
        return memberRepository.save(initialMember);
    }

    public void deleteMember(Long memberId) {
        Member findMember = findVerified(memberId);
        memberRepository.delete(findMember);
    }

    /**
     * @param memberId dataBase에 파라미터로 받은 memberId의 member 객체 탐색
     * @return memberId가 동일한 member 반환
     */
    public Member findVerified(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return findMember;
    }

    /**
     * @param email or nickname -> email or nickname
     *                   email 또는 nickname을 입력 받았을 때 dataBase에 동일한 email 또는 nickname이 존재하는지
     *                   판단 후 존재할 경우 errorResponse 발생
     */
    private void verifyExistEmail(String email) {
        Optional<Member> memberEmail = memberRepository.findByEmail(email);
        if (memberEmail.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EMAIL_EXIST);
        }
    }
    private void verifyExistNickname(String nickname) {
        Optional<Member> memberNickname = memberRepository.findByNickname(nickname);
        if (memberNickname.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_NICKNAME_EXIST);
        }
    }

    public Long findMemberId(HttpServletRequest request) {
        Map<String, Object> claims = jwtVerificationFilter.verifyJws(request);
        long userId = ((Number)claims.get("userId")).longValue();

        log.info("userId -> {}", userId);
        return userId;
    }
}
