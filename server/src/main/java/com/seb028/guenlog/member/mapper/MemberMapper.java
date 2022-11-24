package com.seb028.guenlog.member.mapper;

import com.seb028.guenlog.member.dto.MemberDto;
import com.seb028.guenlog.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.post requestBody);

    Member memberPatchDtoToMember(MemberDto.patch requestBody);

//    MemberDto.response memberToMemberResponseDto(Member member);
}
