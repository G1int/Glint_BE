package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "university_category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniversityCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_category_id")
    private Long id;

    @Column(name = "university_category_name")
    private  String universityCategoryName;

    @Builder(access = AccessLevel.PRIVATE)
    private UniversityCategory(Long id, String universityCategoryName) {
        this.id = id;
        this.universityCategoryName = universityCategoryName;
    }
}
