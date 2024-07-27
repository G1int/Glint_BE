package com.swyp.glint.keyword.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import com.swyp.glint.user.domain.UserDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "work")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Work extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long id;

    @Column(name = "work_name")
    private String workName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_category_id")
    private WorkCategory workCategory;

    @Builder(access = AccessLevel.PRIVATE)
    private Work(Long id, String workName, WorkCategory workCategory) {
        this.id = id;
        this.workName = workName;
        this.workCategory = workCategory;
    }

    public static Work createNewWork(String workName) {
        return Work.builder()
                .workName(workName)
                .build();
    }

    public void updateWork(String workName, WorkCategory workCategory) {
        this.workName = workName;
        this.workCategory = workCategory;
    }

}
