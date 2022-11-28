package com.seb028.guenlog.member.entity;


import com.seb028.guenlog.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private LocalDate age;

    private Integer height;

    private Character gender;
    //최초 로그인 판별을 위한 필드 추가
    private Boolean initialLogin;
    @PrePersist
    public void prePersist(){
        this.initialLogin = false;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public Member(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
    public Member update(String email, String name){
        this.email = email;
        this.nickname = name;

        return this;
    }
}
