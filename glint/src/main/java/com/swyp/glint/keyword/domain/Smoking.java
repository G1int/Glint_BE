package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "smoking")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Smoking extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "smoking_name")
    private String smokingName;

    @Builder(access = AccessLevel.PRIVATE)
    private Smoking(Long id, String smokingName) {
        this.id = id;
        this.smokingName = smokingName;
    }

}
