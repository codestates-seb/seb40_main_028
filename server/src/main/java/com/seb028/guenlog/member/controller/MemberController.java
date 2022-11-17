package com.seb028.guenlog.member.controller;

import com.seb028.guenlog.member.dto.MemberDto;
import com.seb028.guenlog.member.entity.Member;
import com.seb028.guenlog.member.mapper.MemberMapper;
import com.seb028.guenlog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
