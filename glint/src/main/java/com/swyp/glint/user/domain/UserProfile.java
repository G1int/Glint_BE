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
    private University university;

    @Embedded
    private Location location;

    @Column(name = "religion_id")
    private Long religionId;

    @Column(name = "smoking_id")
    private Long smokingId;

    @Column(name = "drinking_id")
    private Long drinkingId;

    @Column
    private String selfIntroduction;

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
   public UserProfile(Long id, Long userId, Work work, University university, Location location, Long religionId,
                      Long smokingId, Long drinkingId, String selfIntroduction, List<String> hashtags) {
       this.id = id;
       this.userId = userId;
       this.work = work;
       this.university = university;
       this.location = location;
       this.religionId = religionId;
       this.smokingId = smokingId;
       this.drinkingId = drinkingId;
       this.selfIntroduction = selfIntroduction;
       this.hashtags = hashtags;
   }
}
