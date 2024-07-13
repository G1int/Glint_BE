package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "location")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private String state; // 시, 도

    @Column(name = "city")
    private String city; // 시, 군, 구

    @Builder(access = AccessLevel.PRIVATE)
    private Location(Long id, String state, String city) {
        this.id = id;
        this.state = state;
        this.city = city;
    }

}
