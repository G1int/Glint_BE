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
    @Column(name = "work_category_id")
    private Long id;

    @Column(name = "work_category_keyword")
    private String workCategoryKeyword; // 의사, 삼성, 한국전력공사, 7급

    @Column(name = "work_category_name")
    private String workCategoryName;

    @Builder(access = AccessLevel.PRIVATE)
    private WorkCategory(Long id, String workCategoryKeyword, String workCategoryName) {
        this.id = id;
        this.workCategoryKeyword = workCategoryKeyword;
        this.workCategoryName = workCategoryName;
    }

}
