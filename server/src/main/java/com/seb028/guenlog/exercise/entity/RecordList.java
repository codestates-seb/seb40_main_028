package com.seb028.guenlog.exercise.entity;


import com.seb028.guenlog.base.BaseEntity;
import com.seb028.guenlog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordList  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_list_id")
    private Long id;


    @Column(name = "total_time")
    private Time  totalTime;

    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

}
