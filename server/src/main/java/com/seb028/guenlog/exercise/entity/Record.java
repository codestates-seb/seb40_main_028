package com.seb028.guenlog.exercise.entity;


import com.seb028.guenlog.base.BaseEntity;
import com.seb028.guenlog.exercise.dto.ExercisePlanRequestDto;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
//@ToString(exclude = {"exercise","member","records"})
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercise_id")
    private Exercise exercise;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="today_id")
    private Today today;

    private Boolean isCompleted;


    @Type(type = "json")
    @Column(columnDefinition="json" , name = "each_records")
    private List<ExercisePlanRequestDto.EachRecords> eachRecords;



}
