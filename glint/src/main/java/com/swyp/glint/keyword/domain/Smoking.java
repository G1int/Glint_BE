package com.swyp.glint.keyword.domain;

import com.swyp.glint.core.common.baseentity.BaseTimeEntity;
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
    @Column(name = "smoking_id")
    private Long id;

    @Column(name = "smoking_name")
    private String smokingName;

    @Builder(access = AccessLevel.PRIVATE)
    private Smoking(Long id, String smokingName) {
        this.id = id;
        this.smokingName = smokingName;
    }

    public static Smoking createNewSmoking(String smokingName) {
        return Smoking.builder()
                .smokingName(smokingName)
                .build();
    }

    public static Smoking createNewSmoking(Long id, String smokingName) {
        return Smoking.builder()
                .id(id)
                .smokingName(smokingName)
                .build();
    }

    public void updateSmoking(String smokingName) {
        this.smokingName = smokingName;
    }
}
