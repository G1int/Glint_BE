package com.swyp.glint.meeting.business;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.meeting.domain.*;
import com.swyp.glint.user.domain.Gender;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMeetingValidatorTest {


    @Test
    @DisplayName("사용자의 키 조건이 만족하지 않을경우 실패한다.")
    public void validateUserMeetingFailHeightCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion("무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isFalse();

    }


    @Test
    @DisplayName("사용자의 키 조건이 만족할 경우 성공한다.")
    public void validateUserMeetingHeightCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion("무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("HEIGHT"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isTrue();
    }



    @Test
    @DisplayName("사용자의 나이 조건이 만족할 경우 성공한다.")
    public void validateUserMeetingAgeCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion("무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1999, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "AGE"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isTrue();
    }



    @Test
    @DisplayName("사용자의 나이 조건이 만족하지 않을 경우 실패한다.")
    public void validateUserMeetingFailAgeCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion("무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1995, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "AGE"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AGE"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isTrue();
    }


    @Test
    @DisplayName("사용자의 직장,학령 조건이 만족할 경우 성공한다.")
    public void validateUserMeetingAffiliationCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion("무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1999, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "AFFILIATION"),
                        List.of("삼성전자","서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AFFILIATION"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isTrue();
    }



    @Test
    @DisplayName("사용자의 직장,학령 조건이 만족하지 않을 경우 실패한다.")
    public void validateUserMeetingFailAffiliationCondition() {

        // given
        Work work = Work.createNewWork("구글");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "MIT",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion("무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1995, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "AFFILIATION"),
                        List.of("삼성전자", "서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("AFFILIATION"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isFalse();
    }



    @Test
    @DisplayName("사용자의 종교 조건이 만족할 경우 성공한다.")
    public void validateUserMeetingReligionCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion(1L,"무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1999, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "RELIGION"),
                        List.of("삼성전자","서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(1L),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("RELIGION"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(1L),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isTrue();
    }



    @Test
    @DisplayName("사용자의 직장,학령 조건이 만족하지 않을 경우 실패한다.")
    public void validateUserMeetingFailReligionCondition() {

        // given
        Work work = Work.createNewWork("구글");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "MIT",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion(1L,"무교");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1995, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "RELIGION"),
                        List.of("삼성전자", "서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(2L),
                        List.of(),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("RELIGION"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(2L),
                        List.of(),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isFalse();
    }


    @Test
    @DisplayName("사용자의 종교 조건이 만족할 경우 성공한다.")
    public void validateUserMeetingSmokingCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion(1L,"무교");
        Smoking smoking = Smoking.createNewSmoking(1L, "비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1999, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "SMOKING"),
                        List.of("삼성전자","서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(1L),
                        List.of(1L),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("SMOKING"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(1L),
                        List.of(1L),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isTrue();
    }



    @Test
    @DisplayName("사용자의 직장,학령 조건이 만족하지 않을 경우 실패한다.")
    public void validateUserMeetingFailSmokingCondition() {

        // given
        Work work = Work.createNewWork("구글");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "MIT",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion(1L,"무교");
        Smoking smoking = Smoking.createNewSmoking(1L,"비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1995, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "SMOKING"),
                        List.of("삼성전자", "서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(2L),
                        List.of(2L),
                        List.of()
                ),
                JoinConditionElement.createNew(
                        List.of("SMOKING"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(2L),
                        List.of(2L),
                        List.of()
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isFalse();
    }



    @Test
    @DisplayName("사용자의 종교 조건이 만족할 경우 성공한다.")
    public void validateUserMeetingDrinkingCondition() {

        // given
        Work work = Work.createNewWork("삼성전자");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "서울대학교",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion(1L,"무교");
        Smoking smoking = Smoking.createNewSmoking(1L, "비흡연");
        Drinking drinking = Drinking.createNewDrinking(2L, "가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1999, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "DRINKING"),
                        List.of("삼성전자","서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(1L),
                        List.of(1L),
                        List.of(2L)
                ),
                JoinConditionElement.createNew(
                        List.of("DRINKING"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(1L),
                        List.of(1L),
                        List.of(2L)
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isTrue();
    }



    @Test
    @DisplayName("사용자의 직장,학령 조건이 만족하지 않을 경우 실패한다.")
    public void validateUserMeetingFailDrinkingCondition() {

        // given
        Work work = Work.createNewWork("구글");
        UniversityCategory universityCategory = UniversityCategory.create("명문대");
        University university = University.createNewUniversity(
                "MIT",
                "컴퓨터공학",
                universityCategory);

        Location location = Location.createNewLocation(
                "서울시",
                "종로구");
        Religion religion = Religion.createNewReligion(1L,"무교");
        Smoking smoking = Smoking.createNewSmoking(1L,"비흡연");
        Drinking drinking = Drinking.createNewDrinking(2L, "가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "coffee",
                Gender.MALE.name(),
                LocalDate.of(1995, 01, 01),
                175,
                "profileImage"
        );

        UserDetail leaderUserDetail = UserDetail.createNewUserDetail(
                4L,
                "donut",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                160,
                "profileImage"
        );

        Meeting meeting = Meeting.createNewMeeting(
                "모두 모여라",
                "너만 오면 시작",
                1L,
                List.of(1L, 2L),
                JoinConditionElement.createNew(
                        List.of( "DRINKING"),
                        List.of("삼성전자", "서울대학교"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(170, 180),
                        List.of(2L),
                        List.of(2L),
                        List.of(1L)
                ),
                JoinConditionElement.createNew(
                        List.of("DRINKING"),
                        List.of("삼성전자"),
                        AgeRange.createNew(20, 30),
                        HeightRange.createNew(160, 180),
                        List.of(2L),
                        List.of(2L),
                        List.of(1L)
                ),
                4
        );

        // when
        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfile,
                userDetail,
                leaderUserDetail,
                meeting
        );

        // then
        assertThat(userMeetingValidator.isValidate()).isFalse();
    }

}
