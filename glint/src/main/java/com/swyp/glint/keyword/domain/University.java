package com.swyp.glint.keyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "university")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @Column(name = "university_name")
    private  String universityName;

    @Column(name = "university_department")
    private  String universityDepartment;

    @Column(name = "university_category_id")
    private  Long universityCategoryId;

    @Builder(access = AccessLevel.PRIVATE)
    private University(Long id, String universityName, String universityDepartment, Long universityCategoryId) {
        this.id = id;
        this.universityName = universityName;
        this.universityDepartment = universityDepartment;
        this.universityCategoryId = universityCategoryId;
    }

    public static University createNewUniversity(String universityName, String universityDepartment) {
        return University.builder()
                .universityName(universityName)
                .universityDepartment(universityDepartment)
                .build();
    }

    public void updateUniversity(String universityName, String universityDepartment) {
        this.universityName = universityName;
        this.universityDepartment = universityDepartment;
    }

}
