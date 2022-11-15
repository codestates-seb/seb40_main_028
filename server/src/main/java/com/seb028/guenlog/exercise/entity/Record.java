package com.seb028.guenlog.exercise.entity;

import com.seb028.guenlog.base.BaseEntity;
import com.seb028.guenlog.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Record extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    private Integer weight;

    private Integer count;

    @Column(name = "set_count")
    private Integer setCount;

    @Column(name = "due_date")
    private Date dueDate;

    private Boolean completed;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercise_id")
    private Exercise exercise;
}
