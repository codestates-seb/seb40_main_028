package com.seb028.guenlog.member.mapper;

import com.seb028.guenlog.member.dto.MyPageResponseDto;
import com.seb028.guenlog.member.util.MyPage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MyPageMapper {
    MyPageResponseDto myPageToMyPageResponseDto(MyPage myPage);
}
