package com.seb028.guenlog.exercise.entity;


import com.seb028.guenlog.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.ALL}  )
    private List<Exercise> exercises = new ArrayList<>();;

}
