package com.seb028.guenlog.member.service;

import com.seb028.guenlog.exception.BusinessLogicException;
import com.seb028.guenlog.exception.ExceptionCode;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.repository.MemberWeightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberWeightService {

    private final MemberWeightRepository memberWeightRepository;

    public MemberWeightService(MemberWeightRepository memberWeightRepository) {
        this.memberWeightRepository = memberWeightRepository;
    }

    // 사용자의 가장 최근 몸무게 기록 하나 조회
    @Transactional(readOnly = true)
    public MemberWeight findRecentOneWeight(long memberId) {
        // memberWeightRepository에서 memberId를 이용해 몸무게 기록들 최신순으로 조회
        List<MemberWeight> findMemberWeights = memberWeightRepository.findRecentByMemberId(memberId);

        // 가장 최근 몸무게 객체 (0번째 인덱스 값)
        MemberWeight memberWeight = findMemberWeights.get(0);

        return memberWeight;
    }

    // 사용자의 몸무게 새로 추가
    @Transactional(propagation = Propagation.REQUIRED)
    public MemberWeight createMemberWeight(MemberWeight memberWeight) {
        MemberWeight savedMemberWeight = memberWeightRepository.save(memberWeight);

        return savedMemberWeight;
    }
}
