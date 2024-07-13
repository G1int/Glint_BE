package com.swyp.glint.keyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "work")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_name")
    private  String workName;

    @Column(name = "work_category_id")
    private  Long workCategoryId;

    @Builder(access = AccessLevel.PRIVATE)
    private Work(Long id, String workName, Long workCategoryId) {
        this.id = id;
        this.workName = workName;
        this.workCategoryId = workCategoryId;
    }

}
