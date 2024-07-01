package com.swyp.glint.user.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "role")
    private String role;

    @Column(name = "provider")
    // GOOGLE
    private String provider;

    @Column(name = "archived")
    private Boolean archived;

    @Column
    private Long userDetailId;

    @Column
    private Long userProfileId;

    @Builder(access = AccessLevel.PRIVATE)
    private User(Long id, String name, String email, String gender, String role, String provider, Boolean archived) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.role = role;
        this.provider = provider;
        this.archived = archived;
    }



    public static User createNewUser(String name, String email, String gender, String role, String provider){
        return User.builder()
                .name(name)
                .email(email)
                .gender(gender)
                .role(role)
                .archived(false)
                .provider(provider)
                .build();
    }


}
