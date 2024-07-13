package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "religion")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Religion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "religion_name")
    private String religionName;

    @Builder(access = AccessLevel.PRIVATE)
    private Religion(Long id, String religionName) {
        this.id = id;
        this.religionName = religionName;
    }

}
