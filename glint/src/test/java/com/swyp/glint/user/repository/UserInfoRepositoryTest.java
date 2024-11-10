package com.swyp.glint.user.repository;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.keyword.repository.*;
import com.swyp.glint.user.domain.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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

        assertThat(userInfoOptional).isPresent();
        UserInfo userInfo = userInfoOptional.get();
        UserDetail foundUserDetail = userInfo.getUserDetail();
        UserProfile foundUserProfile = userInfo.getUserProfile();

        assertThat(userInfo.getUserId()).isEqualTo(user.getId());

        assertThat(foundUserDetail.getUserId()).isEqualTo(userDetail.getUserId());
        assertThat(foundUserDetail.getNickname()).isEqualTo("nickname");
        assertThat(foundUserDetail.getGender()).isEqualTo(Gender.MALE.name());
        assertThat(foundUserDetail.getBirthdate()).isEqualTo(LocalDate.of(1990, 01, 01));
        assertThat(foundUserDetail.getHeight()).isEqualTo(185);
        assertThat(foundUserDetail.getProfileImage()).isEqualTo("80");

        assertThat(foundUserProfile.getUserId()).isEqualTo(userProfile.getUserId());
        assertThat(foundUserProfile.getWork().getWorkName()).isEqualTo("workName");
        assertThat(foundUserProfile.getUniversity().getUniversityName()).isEqualTo("universityName");
        assertThat(foundUserProfile.getUniversity().getUniversityDepartment()).isEqualTo("universityMajor");
        assertThat(foundUserProfile.getUniversity().getUniversityCategory().getUniversityCategoryName()).isEqualTo("대학교");

        assertThat(foundUserProfile.getLocation().getState()).isEqualTo("locationName");
        assertThat(foundUserProfile.getLocation().getCity()).isEqualTo("locationStatus");

        assertThat(foundUserProfile.getReligion().getReligionName()).isEqualTo("religionName");
        assertThat(foundUserProfile.getSmoking().getSmokingName()).isEqualTo("비흡연");
        assertThat(foundUserProfile.getDrinking().getDrinkingName()).isEqualTo("가끔마심");
        assertThat(foundUserProfile.getSelfIntroduction()).isEqualTo("안녕하세요");
        assertThat(foundUserProfile.getHashtags()).contains("비흡연", "INTJ");

    }

}
