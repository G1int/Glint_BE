package com.swyp.glint.user.domain;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public static Gender getOtherGender(Gender gender) {
        if(gender.equals(Gender.FEMALE)) {
            return Gender.MALE;
        }

        return Gender.FEMALE;
    }
}
