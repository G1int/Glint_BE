package com.swyp.glint.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConditionKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name;

    private String type;


    @Builder(access = AccessLevel.PRIVATE)
    private ConditionKeyword(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }


    public static ConditionKeyword createNew(String name, String type){
        return ConditionKeyword.builder()
                .name(name)
                .type(type)
                .build();
    }

}
