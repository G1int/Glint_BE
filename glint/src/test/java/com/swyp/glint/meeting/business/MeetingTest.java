package com.swyp.glint.meeting.business;

import com.swyp.glint.meeting.domain.AgeRange;
import com.swyp.glint.meeting.domain.HeightRange;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.exception.NumberOfPeopleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MeetingTest {

    @Test
    @DisplayName("미팅에 참가여부를 userId를 통해 확인할 수 있다.")
    public void isJoinUser() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        Long joinUserId = 1L;


        // When
        meeting.isJoinUser(1L);

        // Then
        assertThat(meeting.isJoinUser(joinUserId)).isTrue();

    }

    @Test
    @DisplayName("미팅에 미참가 여부를 userId를 통해 확인할 수 있다.")
    public void isNotJoinUser() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        Long joinUserId = 3L;


        // When
        meeting.isJoinUser(joinUserId);

        // Then
        assertThat(meeting.isNotJoinUser(joinUserId)).isTrue();
    }


    @Test
    @DisplayName("미팅에 참가자를 추가할 수 있다.")
    public void addUser() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        Long addUserId = 3L;


        // When
        meeting.addUser(addUserId);

        // Then
        assertThat(meeting.getJoinUserIds()).contains(addUserId);
    }

    @Test
    @DisplayName("미팅 유저 초과시 정원이 초과되면 예외를 발생시킨다.")
    public void addUserFailWhenOverJoinUserCount() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                2
        );
        meeting.addUser(2L);
        meeting.addUser(3L);
        meeting.addUser(4L);

        Long addUserId = 5L;

        // When
        // Then
        assertThatThrownBy(() -> meeting.addUser(addUserId))
                .isInstanceOf(NumberOfPeopleException.class)
                .hasMessageContaining("Over Number of people");
    }

    @Test
    @DisplayName("미팅에 참가자를 내보낼 수 있다.")
    public void outUser() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        meeting.addUser(2L);
        Long outUserId = 2L;


        // When
        meeting.outUser(outUserId);

        // Then
        assertThat(meeting.getJoinUserIds()).containsExactly(1L);
    }

    @Test
    @DisplayName("미팅에 참가인원이 혼자인지 여부를 확인할 수 있다.")
    public void isAlone() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );



        // When
        boolean alone = meeting.isAlone();

        // Then
        assertThat(alone).isTrue();
    }


    @Test
    @DisplayName("미팅에 리더를 제외한 유저가 참여했는지 여부를 판단할 수 있다.")
    public void isJoinedUser() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );


        Meeting fullMeeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                1
        );
        fullMeeting.addUser(2L);


        // When
        boolean aloneMeeting = meeting.isJoinedUser();
        boolean JoinedUser = fullMeeting.isJoinedUser();

        // Then
        assertThat(aloneMeeting).isFalse();
        assertThat(JoinedUser).isTrue();
    }




    @Test
    @DisplayName("미팅이 Waiting 일 경우에만 수정이 가능하다.")
    public void isUpdatable() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );



        // When
        boolean isNotUpdatable = meeting.isNotUpdatable();
        boolean isUpdatable = meeting.isUpdatable();

        // Then
        assertThat(isNotUpdatable).isFalse();
        assertThat(isUpdatable).isTrue();
    }



    @Test
    @DisplayName("미팅을 수정할 수 있다.")
    public void update() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        Meeting toUpdateMeeting = Meeting.createNewMeeting(
                "모여모여",
                "일단 들어와",
                1L,
                List.of( 2L),
                JoinConditionElement.createNew(
                        List.of("DRINKING"),
                        List.of("서울대학교"),
                        AgeRange.createNew(20, 25),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("DRINKING"),
                        List.of("서울대학교"),
                        AgeRange.createNew(20, 25),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );



        // When
        meeting.update(toUpdateMeeting);

        // Then
        assertThat(meeting.getTitle()).isEqualTo("모여모여");
        assertThat(meeting.getDescription()).isEqualTo("일단 들어와");
        assertThat(meeting.getLeaderUserId()).isEqualTo(1L);
        assertThat(meeting.getJoinUserIds()).containsExactly(1L);
        assertThat(meeting.getLocationIds()).containsExactly(2L);
        assertThat(meeting.getMaleCondition()).satisfies(maleCondition -> {
            assertThat(maleCondition.getSelectConditions()).containsExactly("DRINKING");
            assertThat(maleCondition.getAffiliation()).containsExactly("서울대학교");
            assertThat(maleCondition.getAgeRange()).satisfies(ageRange -> {
                assertThat(ageRange.getMinAge()).isEqualTo(20);
                assertThat(ageRange.getMaxAge()).isEqualTo(25);
            });
            assertThat(maleCondition.getHeightRange()).satisfies(heightRange -> {
                assertThat(heightRange.getMinHeight()).isEqualTo(160);
                assertThat(heightRange.getMaxHeight()).isEqualTo(180);
            });
        });
        assertThat(meeting.getFemaleCondition()).satisfies(
                femaleCondition -> {
                    assertThat(femaleCondition.getSelectConditions()).containsExactly("DRINKING");
                    assertThat(femaleCondition.getAffiliation()).containsExactly("서울대학교");
                    assertThat(femaleCondition.getAgeRange()).satisfies(ageRange -> {
                        assertThat(ageRange.getMinAge()).isEqualTo(20);
                        assertThat(ageRange.getMaxAge()).isEqualTo(25);
                    });
                    assertThat(femaleCondition.getHeightRange()).satisfies(heightRange -> {
                        assertThat(heightRange.getMinHeight()).isEqualTo(160);
                        assertThat(heightRange.getMaxHeight()).isEqualTo(180);
                    });
                }
        );
        assertThat(meeting.getPeopleCapacity()).isEqualTo(4);

    }

    @Test
    @DisplayName("미팅의 Drinking 조건의 id를 모두 조회할 수 있다.")
    public void getAllDrinkingIds() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of(1L)
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of(2L)
                ),
                4
        );

        // When
        List<Long> allDrinkingIds = meeting.getAllDrinkingIds();

        // Then
        assertThat(allDrinkingIds).containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("미팅의 Smoking 조건의 id를 모두 조회할 수 있다.")
    public void getAllSmokingIds() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(1L),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(2L),
                        List.of()
                ),
                4
        );

        // When
        List<Long> allSmokingIds = meeting.getAllSmokingIds();

        // Then
        assertThat(allSmokingIds).containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("미팅의 Religion 조건의 id를 모두 조회할 수 있다.")
    public void getAllReligionIds() {
        // Given
        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(1L),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE", "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(2L),
                        List.of(),
                        List.of()
                ),
                4
        );

        // When
        List<Long> allReligionIds = meeting.getAllReligionIds();

        // Then
        assertThat(allReligionIds).containsExactly(1L, 2L);
    }



}
