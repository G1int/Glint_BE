package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
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
    private Long id;

    @Column(name = "drinking_state")
    private String state;

    @Builder(access = AccessLevel.PRIVATE)
    private Drinking(Long id, String state) {
        this.id = id;
        this.state = state;
    }

}
