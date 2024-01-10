package com.bloodDonation.member.service;

import com.bloodDonation.member.entities.Member;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {
    //외부에서 세팅하면 쓸 수 있게 넣어준다
    private String email;
    private String userId;
    private String password;
    private Member member;

    private Collection<? extends GrantedAuthority> authorities;

    //권한 체크
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        //이메일이 있으면 이메일 없으면 userId로
        return StringUtils.hasText(email) ? email : userId;
    }

    //계정이 만료된 상태인지 체크
    //false이면 로그인이 안되므로
    //우선 true로 바꿔주고 추후 관리자 설정에서 바꿔주자
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //만료된 비번인지 상태 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //탈퇴시 기존정보를 유지하지만 로그인 못하게 만들어줌
    @Override
    public boolean isEnabled() {

        return true;
    }
}
