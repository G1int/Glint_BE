package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "work_category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_category_id")
    private Long id;

    @ElementCollection
    @CollectionTable(name = "work_category_keywords", joinColumns = @JoinColumn(name = "work_category_id"))
    @Column(name = "keyword")
    private List<String> workCategoryKeywords; // 의사, 삼성, 한국전력공사, 7급

    @Column(name = "work_category_name")
    private String workCategoryName;

    @Builder(access = AccessLevel.PRIVATE)
    private WorkCategory(Long id, List<String> workCategoryKeywords, String workCategoryName) {
        this.id = id;
        this.workCategoryKeywords = workCategoryKeywords;
        this.workCategoryName = workCategoryName;
    }

}
