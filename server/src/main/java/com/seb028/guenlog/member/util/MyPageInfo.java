package com.seb028.guenlog.member.util;

import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageInfo {
    private Member member;
    private MemberWeight memberWeight;
}
