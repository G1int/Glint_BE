package com.swyp.glint.user.business;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.domain.UserProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UserProfileTest {

    

    @Test
    @DisplayName("유저 프로필을 수정할 수 있다.")
    public void updateUserProfile() {
        //give
        Work work = Work.createNewWork("workName");
        UniversityCategory universityCategory = UniversityCategory.create("대학교");
        University university = University.createNewUniversity(
                "universityName",
                "universityMajor",
                universityCategory);
        
        Location location = Location.createNewLocation(
                "locationName",
                "locationStatus");
        Religion religion = Religion.createNewReligion("religionName");
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

        
        //when
        userProfile.updateUserProfile(
                Work.createNewWork("updateWorkName"),
                University.createNewUniversity(
                        "updateUniversityName",
                        "updateUniversityMajor",
                        UniversityCategory.create("전문대학교")
                ),
                Location.createNewLocation(
                        "updateLocationState",
                        "updateLocationCity"),
                Religion.createNewReligion("updateReligionName"),
                Smoking.createNewSmoking("흡연"),
                Drinking.createNewDrinking("매일마심"),
                "안녕하세요",
                List.of("흡연", "ENTJ")
        );

        //then

        Assertions.assertThat(userProfile.getWork().getWorkName()).isEqualTo("updateWorkName");
        Assertions.assertThat(userProfile.getUniversity().getUniversityName()).isEqualTo("updateUniversityName");
        Assertions.assertThat(userProfile.getUniversity().getUniversityCategory().getUniversityCategoryName()).isEqualTo("전문대학교");
        Assertions.assertThat(userProfile.getLocation().getCity()).isEqualTo("updateLocationCity");
        Assertions.assertThat(userProfile.getLocation().getState()).isEqualTo("updateLocationState");
        Assertions.assertThat(userProfile.getReligion().getReligionName()).isEqualTo("updateReligionName");
        Assertions.assertThat(userProfile.getSmoking().getSmokingName()).isEqualTo("흡연");
        Assertions.assertThat(userProfile.getDrinking().getDrinkingName()).isEqualTo("매일마심");
        Assertions.assertThat(userProfile.getSelfIntroduction()).isEqualTo("안녕하세요");
        Assertions.assertThat(userProfile.getHashtags()).containsExactly("흡연", "ENTJ");
    }


    @Test
    @DisplayName("work가 null이 아닌경우 work를 반환한다.")
    public void getAffiliationWorkIsNotNull() {
        //give
        Work work = Work.createNewWork("workName");
        UniversityCategory universityCategory = UniversityCategory.create("대학교");
        University university = University.createNewUniversity(
                "universityName",
                "universityMajor",
                universityCategory);

        Location location = Location.createNewLocation(
                "locationName",
                "locationStatus");
        Religion religion = Religion.createNewReligion("religionName");
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


        //when
        String affiliation = userProfile.getAffiliation();

        //then
        Assertions.assertThat(userProfile.getWork()).isNotNull();
        Assertions.assertThat(affiliation).isEqualTo("workName");
    }


    @Test
    @DisplayName("work가 null인 경우 University가 있을시 University를 반환한다.")
    public void getAffiliationWorkIsNull() {
        //give
        UniversityCategory universityCategory = UniversityCategory.create("대학교");
        University university = University.createNewUniversity(
                "universityName",
                "universityMajor",
                universityCategory);

        Location location = Location.createNewLocation(
                "locationName",
                "locationStatus");
        Religion religion = Religion.createNewReligion("religionName");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                null,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        //when
        String affiliation = userProfile.getAffiliation();

        //then
        Assertions.assertThat(userProfile.getWork()).isNull();
        Assertions.assertThat(affiliation).isEqualTo("universityName");
    }


    @Test
    @DisplayName("work와 University가 둘다 null인 경우 null을 반환한다.")
    public void getAffiliationWorkAndUniversityIsNull() {
        //give
        UniversityCategory universityCategory = UniversityCategory.create("대학교");
        Location location = Location.createNewLocation(
                "locationName",
                "locationStatus");
        Religion religion = Religion.createNewReligion("religionName");
        Smoking smoking = Smoking.createNewSmoking("비흡연");
        Drinking drinking = Drinking.createNewDrinking("가끔마심");
        UserProfile userProfile = UserProfile.createNewUserProfile(
                1L,
                null,
                null,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );


        //when
        String affiliation = userProfile.getAffiliation();

        //then
        Assertions.assertThat(userProfile.getWork()).isNull();
        Assertions.assertThat(userProfile.getUniversity()).isNull();
        Assertions.assertThat(affiliation).isNull();
    }

}
