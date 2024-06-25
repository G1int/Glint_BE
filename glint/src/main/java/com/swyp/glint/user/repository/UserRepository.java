package com.swyp.glint.user.repository;

import com.swyp.glint.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
