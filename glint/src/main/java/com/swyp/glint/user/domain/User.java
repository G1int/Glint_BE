package com.swyp.glint.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Builder(access = AccessLevel.PRIVATE)
    private User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }


    public static User createNewUser(String username, String email){
        return User.builder()
                .username(username)
                .email(email)
                .build();
    }

    public void updateUserName(String newUser) {
        this.username = newUser;
    }


}
