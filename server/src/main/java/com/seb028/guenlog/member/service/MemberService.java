package com.seb028.guenlog.member.service;

import com.seb028.guenlog.config.auth.jwt.filter.JwtVerificationFilter;
import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtVerificationFilter jwtVerificationFilter;

    private final PasswordEncoder passwordEncoder;


    /**
     * @param member : email, nickname, password 입력 받아 Member 객체 생성
     * @return MemberRepository -> dataBase에 member 객체 저장
     */
    public Member createMember(Member member) {
        verifyExistMember(member.getEmail());
        verifyExistMember(member.getNickname());
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member);
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
     * @param attributes -> email or nickname
     *                   email 또는 nickname을 입력 받았을 때 dataBase에 동일한 email 또는 nickname이 존재하는지
     *                   판단 후 존재할 경우 errorResponse 발생
     */
    private void verifyExistMember(String attributes) {
        Optional<Member> memberEmail = memberRepository.findByEmail(attributes);
        Optional<Member> memberNickname = memberRepository.findByNickname(attributes);
        if (memberEmail.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EMAIL_EXIST);
        }
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
