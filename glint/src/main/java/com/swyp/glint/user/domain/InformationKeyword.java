package com.swyp.glint.user.domain;

import jakarta.persistence.*;

@Entity
public class InformationKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;


}
