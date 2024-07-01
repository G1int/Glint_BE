package com.swyp.glint.auth.application;

import com.swyp.glint.user.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


// 로그인 요청이 오면 인터셉트
// 로그인을 진행이 완료가 되면 시큐리티 session을 만들어줌.(Security ContextHOlder)
// 오브젝트 타입 => Authentication 타입 객체
// authentication 안에 User정보가있어야 됨.
// User오브젝트 타입 => UserDetails 타입 객체
// Security Session => Authentication => UserDetails(PrincipalDetails)
@Getter
public class UserPrincipalDetails implements UserDetails, OAuth2User {

    private User user;

    private Map<String,Object> attributes;

    public UserPrincipalDetails(User user) {
        this.user = user;
    }


    public UserPrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((GrantedAuthority) () -> user.getRole());
        return collect;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Map<String, Object> getAttributes() {

        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
