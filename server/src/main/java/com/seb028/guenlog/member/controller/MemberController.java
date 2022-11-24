package com.seb028.guenlog.member.controller;

import com.seb028.guenlog.member.dto.MemberDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.mapper.MemberMapper;
import com.seb028.guenlog.member.service.MemberService;
import com.seb028.guenlog.response.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Valid
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity signupUser(@Valid @RequestBody MemberDto.post memberDto) {
        Member member = mapper.memberPostDtoToMember(memberDto);
        memberService.createMember(member);

        return new ResponseEntity<>(new SingleResponseDto<>(null), HttpStatus.CREATED);
    }

    @PatchMapping("/info")
    public ResponseEntity infoUser(@Valid @RequestBody MemberDto.patch memberDto, HttpServletRequest request) {
        //request Token으로 memberId 추출
        long initialMemberId = memberService.findMemberId(request);
        //Member 엔티티에 몸무게를 저장하지 않기위해 입력받은 몸무게를 따로 저장
        Integer memberWeight = memberDto.getWeight();
        memberDto.setId(initialMemberId);
        //Request 받은 정보를 mapper를 이용해 Member 객체로 변환
        Member infoMember = mapper.memberPatchDtoToMember(memberDto);
        //최초 로그인을 시도한 멤버의 추가 정보를 저장
        Member signMember = memberService.initialMember(infoMember, memberWeight);

        return new ResponseEntity<>(new SingleResponseDto<>(null), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(HttpServletRequest request) {
        long deleteId = memberService.findMemberId(request);
        memberService.deleteMember(deleteId);

        return new ResponseEntity<>(new SingleResponseDto<>(null), HttpStatus.NO_CONTENT);
    }
}
