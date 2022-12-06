package com.seb028.guenlog.member.mapper;

import com.seb028.guenlog.member.dto.MyPageInfoDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.entity.MemberWeight;
import com.seb028.guenlog.member.util.MyPageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyPageInfoMapper {
    default MyPageInfoDto.Response myPageInfoToMyPageInfoResponseDto(MyPageInfo myPageInfo) {
        Member member = myPageInfo.getMember();
        MemberWeight memberWeight = myPageInfo.getMemberWeight();

        return MyPageInfoDto.Response.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .gender(member.getGender())
                .height(member.getHeight())
                .weight(memberWeight.getWeight())
                .age(String.valueOf(member.getAge()))
                .build();
    }

    default MyPageInfo myPageInfoPatchDtoToMyPageInfo(MyPageInfoDto.Patch myPageInfoPatchDto) {
        Member member = new Member();
        member.setNickname(myPageInfoPatchDto.getNickname());
        if (myPageInfoPatchDto.getAge() != null)
            member.setAge(LocalDate.parse(myPageInfoPatchDto.getAge()));
        member.setGender(myPageInfoPatchDto.getGender());
        member.setHeight(myPageInfoPatchDto.getHeight());

        MemberWeight memberWeight = MemberWeight.builder()
                .weight(myPageInfoPatchDto.getWeight())
                .member(member)
                .build();

        return MyPageInfo.builder()
                .member(member)
                .memberWeight(memberWeight)
                .build();
    }
}