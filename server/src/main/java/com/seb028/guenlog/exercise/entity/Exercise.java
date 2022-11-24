package com.seb028.guenlog.exercise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seb028.guenlog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exercise  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long id;

    private String name;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY  )
    @JoinColumn(name="category_id")
    private Category category ;


}
