package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "work_category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_category_name")
    private String workCategoryName;

    @Builder(access = AccessLevel.PRIVATE)
    private WorkCategory(Long id, String workCategoryName) {
        this.id = id;
        this.workCategoryName = workCategoryName;
    }

}
