package com.swyp.glint.core.auth.application;

import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.application.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userServiceImpl.getUserByEmail(email);


        return new UserPrincipalDetails(user);
    }
}
