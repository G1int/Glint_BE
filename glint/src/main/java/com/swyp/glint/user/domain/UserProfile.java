package com.swyp.glint.user.domain;

import com.swyp.glint.keyword.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "user_profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile { // 회사or학교, 위치, 자기소개, 키워드(위치, 종교, 흡연여부, 음주여부), 자유 태그("적극적", "ENTJ", "러닝")

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Embedded
    private Work work;

    @Embedded
    private Uni uni;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private Religion religion;

    @Enumerated(EnumType.STRING)
    private Smoking smoking;

    @Enumerated(EnumType.STRING)
    private Drinking drinking;

    //@Column
    //private String shortIntroduction;

    @Column(length = 50)
    private String bio;

    @ElementCollection
    private List<String> hashtags;

   /*
    @Column
    @ElementCollection
    private List<Long> informationKeywordIds;

    @ElementCollection
    private List<String> keywords;
    */

   @Builder(access = AccessLevel.PRIVATE)
   public UserProfile(Long id, Long userId, Work work, Uni uni, Location location, Religion religion,
                      Smoking smoking, Drinking drinking, String bio, List<String> hashtags) {
       this.id = id;
       this.userId = userId;
       this.work = work;
       this.uni = uni;
       this.location = location;
       this.religion = religion;
       this.smoking = smoking;
       this.drinking = drinking;
       this.bio = bio;
       this.hashtags = hashtags;
   }
}
