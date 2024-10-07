package com.swyp.glint.user.repository;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.keyword.repository.*;
import com.swyp.glint.user.domain.*;
import com.swyp.glint.user.infra.UserDetailRepository;
import com.swyp.glint.user.infra.UserProfileRepository;
import com.swyp.glint.user.infra.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserInfoRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

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



    @Transactional
    @DisplayName("UserInfo를 조회할 수 있다.")
    @Test
    public void findUserInfoBy() {

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
                "80"
        );
        userDetailRepository.save(userDetail);

        Work work = workRepository.save(Work.createNewWork("workName"));
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


        //when
        userProfileRepository.save(userProfile);


        //then
        Optional<UserInfo> userInfoOptional = userRepository.findUserInfoBy(user.getId());

        Assertions.assertThat(userInfoOptional).isPresent();
        UserInfo userInfo = userInfoOptional.get();
        UserDetail foundUserDetail = userInfo.getUserDetail();
        UserProfile foundUserProfile = userInfo.getUserProfile();

        Assertions.assertThat(userInfo.getUserId()).isEqualTo(user.getId());

        Assertions.assertThat(foundUserDetail.getUserId()).isEqualTo(userDetail.getUserId());
        Assertions.assertThat(foundUserDetail.getNickname()).isEqualTo("nickname");
        Assertions.assertThat(foundUserDetail.getGender()).isEqualTo(Gender.MALE.name());
        Assertions.assertThat(foundUserDetail.getBirthdate()).isEqualTo(LocalDate.of(1990, 01, 01));
        Assertions.assertThat(foundUserDetail.getHeight()).isEqualTo(185);
        Assertions.assertThat(foundUserDetail.getProfileImage()).isEqualTo("80");

        Assertions.assertThat(foundUserProfile.getUserId()).isEqualTo(userProfile.getUserId());
        Assertions.assertThat(foundUserProfile.getWork().getWorkName()).isEqualTo("workName");
        Assertions.assertThat(foundUserProfile.getUniversity().getUniversityName()).isEqualTo("universityName");
        Assertions.assertThat(foundUserProfile.getUniversity().getUniversityDepartment()).isEqualTo("universityMajor");
        Assertions.assertThat(foundUserProfile.getUniversity().getUniversityCategory().getUniversityCategoryName()).isEqualTo("대학교");

        Assertions.assertThat(foundUserProfile.getLocation().getState()).isEqualTo("locationName");
        Assertions.assertThat(foundUserProfile.getLocation().getCity()).isEqualTo("locationStatus");

        Assertions.assertThat(foundUserProfile.getReligion().getReligionName()).isEqualTo("religionName");
        Assertions.assertThat(foundUserProfile.getSmoking().getSmokingName()).isEqualTo("비흡연");
        Assertions.assertThat(foundUserProfile.getDrinking().getDrinkingName()).isEqualTo("가끔마심");
        Assertions.assertThat(foundUserProfile.getSelfIntroduction()).isEqualTo("안녕하세요");
        Assertions.assertThat(foundUserProfile.getHashtags()).contains("비흡연", "INTJ");

    }

}
