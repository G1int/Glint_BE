package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.domain.MeetingDetail;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user/meeting-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;

    @Transactional
    @Test
    public void findMeetingDetail() {

        // given

        // when

        // then
        Optional<MeetingDetail> meetingDetailOptional = meetingRepository.findMeetingDetail(1L);

        assertThat(meetingDetailOptional).isPresent();


    }

}
