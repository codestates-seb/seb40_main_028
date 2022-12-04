package com.seb028.guenlog.exercise.entity;

import com.seb028.guenlog.base.BaseEntity;
import com.seb028.guenlog.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"member"})
public class Today extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "today_id")
    private Long id;

    @Column(name = "total_time")
    private Integer totalTime;

    @Column(name="due_date" )
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    //Today, Record 연관관계 맵핑
    @OneToMany(mappedBy = "today", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    public void updateTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

}
