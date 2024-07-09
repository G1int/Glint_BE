package com.swyp.glint.keyword.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Uni {
    private String uniName;
    private UniCategory category; // e.g., 'Prestigious universities' and 'Others'

}