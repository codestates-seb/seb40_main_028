package com.seb028.guenlog.member.repository;

import com.seb028.guenlog.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
