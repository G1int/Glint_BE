package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.domain.Join;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinRepository extends JpaRepository<Join, Long> {


}
