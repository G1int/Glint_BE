package com.swyp.glint.user.domain;

import com.swyp.glint.core.common.baseentity.BaseTimeEntity;
import com.swyp.glint.keyword.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile extends BaseTimeEntity { // 회사 or 학교, 위치, 자기소개, 키워드(위치, 종교, 흡연여부, 음주여부), 자유 태그("적극적", "ENTJ", "러닝")

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smoking_id")
    private Smoking smoking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drinking_id")
    private Drinking drinking;

    @Column(name = "self_introduction")
    private String selfIntroduction;

    @ElementCollection
    @CollectionTable(name = "user_profile_hashtags", joinColumns = @JoinColumn(name = "user_profile_id"))
    @Column(name = "hashtag")
    private List<String> hashtags;

    @Builder(access = AccessLevel.PRIVATE)
    public UserProfile(Long id, Long userId, Work work, University university, Location location, Religion religion,
                       Smoking smoking, Drinking drinking, String selfIntroduction, List<String> hashtags) {
        this.id = id;
        this.userId = userId;
        this.work = work;
        this.university = university;
        this.location = location;
        this.religion = religion;
        this.smoking = smoking;
        this.drinking = drinking;
        this.selfIntroduction = selfIntroduction;
        this.hashtags = hashtags;
    }

    public static UserProfile createNewUserProfile(Long userId, Work work, University university, Location location, Religion religion,
                                                   Smoking smoking, Drinking drinking, String selfIntroduction, List<String> hashtags) {
        return UserProfile.builder()
                .userId(userId)
                .work(work)
                .university(university)
                .location(location)
                .religion(religion)
                .smoking(smoking)
                .drinking(drinking)
                .selfIntroduction(selfIntroduction)
                .hashtags(hashtags)
                .build();
    }

    public static UserProfile createEmptyProfile(Long userId) {
        return UserProfile.builder()
                .userId(userId)
                .build();
    }


    public void updateUserProfile(Work work, University university, Location location, Religion religion,
                                  Smoking smoking, Drinking drinking, String selfIntroduction, List<String> hashtags) {
        this.work = work;
        this.university = university;
        this.location = location;
        this.religion = religion;
        this.smoking = smoking;
        this.drinking = drinking;
        this.selfIntroduction = selfIntroduction;
        this.hashtags = hashtags;
    }


    public String getAffiliation() {
        if(Objects.nonNull(work)) {
            return work.getWorkName();

        }

        if(Objects.nonNull(university)) {
            return university.getUniversityName();
        }

        return null;
    }
}
