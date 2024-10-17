package com.swyp.glint.user.application.service;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.keyword.repository.*;
import com.swyp.glint.user.domain.*;
import com.swyp.glint.user.infra.UserDetailRepository;
import com.swyp.glint.user.infra.UserProfileRepository;
import com.swyp.glint.user.infra.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserProfileServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReligionRepository religionRepository;

    @Autowired
    private SmokingRepository smokingRepository;

    @Autowired
    private DrinkingRepository drinkingRepository;

    @Autowired
    private UniversityCategoryRepository universityCategoryRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserSimpleProfileService userSimpleProfileService;

    @Test
    @Transactional
    @DisplayName("User id List를 통해 UserSimpleProfileList를 조회할 수 있다.")
    void getUserSimpleProfileList() {
        //given
        User user = User.createNewUser(
                "test@glint.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );
        userRepository.save(user);

        UserDetail userDetail = UserDetail.createNewUserDetail(
                user.getId(),
                "nickname",
                "MALE",
                LocalDate.of(1990, 01, 01),
                185,
                "image"
        );
        userDetailRepository.save(userDetail);

        Work work = workRepository.save(Work.createNewWork("구글"));
        UniversityCategory universityCategory = UniversityCategory.create("대학교");
        universityCategoryRepository.save(universityCategory);

        University university = universityRepository.save(University.createNewUniversity(
                "universityName",
                "universityMajor",
                universityCategory
        ));

        Location location = locationRepository.save(Location.createNewLocation(
                "locationName",
                "locationStatus"
        ));
        Religion religion = religionRepository.save(Religion.createNewReligion("religionName"));
        Smoking smoking = smokingRepository.save(Smoking.createNewSmoking("비흡연"));
        Drinking drinking = drinkingRepository.save(Drinking.createNewDrinking("가끔마심"));
        UserProfile userProfile = UserProfile.createNewUserProfile(
                user.getId(),
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                "안녕하세요",
                List.of("비흡연", "INTJ")
        );
        userProfileRepository.save(userProfile);



        //when
        List<UserSimpleProfile> userSimpleProfileList = userSimpleProfileService.getUserSimpleProfileList(List.of(user.getId()));

        //then
        assertThat(userSimpleProfileList.size()).isEqualTo(1);
        assertThat(userSimpleProfileList.get(0).getUserId()).isEqualTo(user.getId());
        assertThat(userSimpleProfileList.get(0).getNickname()).isEqualTo("nickname");
        assertThat(userSimpleProfileList.get(0).getGender()).isEqualTo(Gender.MALE.name());
        assertThat(userSimpleProfileList.get(0).getAge()).isEqualTo(34);
        assertThat(userSimpleProfileList.get(0).getProfileImage()).isEqualTo("image");
        assertThat(userSimpleProfileList.get(0).getAffiliation()).isEqualTo("구글");
        assertThat(userSimpleProfileList.get(0).getNickname()).isEqualTo("nickname");


    }

}