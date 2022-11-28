package com.seb028.guenlog.config.auth.dto;

import com.seb028.guenlog.member.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;

    public SessionMember(Member member) {
        this.email = member.getEmail();
        this.name = member.getNickname();
    }
}
