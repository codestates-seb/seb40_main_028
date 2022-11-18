package com.seb028.guenlog.member.mapper;

import com.seb028.guenlog.member.dto.MyPageInfoDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;

public interface MyPageInfoMapper {
    default MyPageInfoDto.Response myPageInfoToMyPageInfoResponseDto(Member member, MemberWeight memberWeight) {
        return MyPageInfoDto.Response.builder()
                .nickname(member.getNickname())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(memberWeight.getWeight())
                .age(member.getAge())
                .build();
    }
}
