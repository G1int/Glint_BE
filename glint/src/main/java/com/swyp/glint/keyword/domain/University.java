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
    private Long id;

    @Column(name = "university_name")
    private  String universityName;

    @Column(name = "university_Category_id")
    private  Long universityCategoryId;

    @Builder(access = AccessLevel.PRIVATE)
    private University(Long id, String universityName, Long universityCategoryId) {
        this.id = id;
        this.universityName = universityName;
        this.universityCategoryId = universityCategoryId;
    }

}
