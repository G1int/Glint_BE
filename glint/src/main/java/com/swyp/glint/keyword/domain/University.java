package com.swyp.glint.keyword.domain;

import com.swyp.glint.core.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "university")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @Column(name = "university_name")
    private  String universityName;

    @Column(name = "university_department")
    private  String universityDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_category_id")
    private UniversityCategory universityCategory;

    @Builder(access = AccessLevel.PRIVATE)
    private University(Long id, String universityName, String universityDepartment, UniversityCategory universityCategory) {
        this.id = id;
        this.universityName = universityName;
        this.universityDepartment = universityDepartment;
        this.universityCategory = universityCategory;
    }

    public static University createNewUniversity(String universityName, String universityDepartment, UniversityCategory universityCategory) {
        return University.builder()
                .universityName(universityName)
                .universityDepartment(universityDepartment)
                .universityCategory(universityCategory)
                .build();
    }

    public void updateUniversity(String universityName, String universityDepartment, UniversityCategory universityCategory) {
        this.universityName = universityName;
        this.universityDepartment = universityDepartment;
        this.universityCategory = universityCategory;
    }
}
