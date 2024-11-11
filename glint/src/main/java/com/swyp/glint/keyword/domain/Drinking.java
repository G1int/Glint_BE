package com.swyp.glint.keyword.domain;

import com.swyp.glint.core.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "drinking")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drinking extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drinking_id")
    private Long id;

    @Column(name = "drinking_name")
    private String drinkingName;

    @Builder(access = AccessLevel.PRIVATE)
    private Drinking(Long id, String drinkingName) {
        this.id = id;
        this.drinkingName = drinkingName;
    }

    public static Drinking createNewDrinking(String drinkingName) {
        return Drinking.builder()
                .drinkingName(drinkingName)
                .build();
    }

    public static Drinking createNewDrinking(Long id, String drinkingName) {
        return Drinking.builder()
                .id(id)
                .drinkingName(drinkingName)
                .build();
    }

    public void updateDrinking(String drinkingName) {
        this.drinkingName = drinkingName;
    }

}
