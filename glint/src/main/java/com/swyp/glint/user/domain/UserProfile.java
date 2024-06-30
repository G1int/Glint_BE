package com.swyp.glint.user.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String shortIntroduction;

    @Column
    private String selfIntroduction;

    @Column
    @ElementCollection
    private List<Long> informationKeywordIds;

    @ElementCollection
    private List<String> keywords;
}
