package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import com.swyp.glint.user.domain.UserProfile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "hashtag")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag extends BaseTimeEntity { //태그 1개 당 1~15자, User당 최대 10개

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id", nullable = false)
    private Long id;

    @Column(name = "tag", nullable = false)
    private String tag;


}
