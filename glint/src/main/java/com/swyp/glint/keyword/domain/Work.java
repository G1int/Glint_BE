package com.swyp.glint.keyword.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Work {
    private String companyName;
    private WorkCategory workCategory; // e.g., Large companies, public enterprises, professionals, civil servants

}
