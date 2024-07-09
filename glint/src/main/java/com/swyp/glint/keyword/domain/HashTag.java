package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import com.swyp.glint.user.domain.UserProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "hashtag")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag extends BaseTimeEntity { //태그 1개 당 1~15자, User당 최대 10개

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hashtag_name")
    private String hashtagName;

    @Builder(access = AccessLevel.PRIVATE)
    private HashTag(Long id, String hashtagName) {
        this.id = id;
        this.hashtagName = hashtagName;
    }

}
