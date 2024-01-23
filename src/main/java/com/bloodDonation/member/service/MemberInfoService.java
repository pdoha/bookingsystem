package com.bloodDonation.member.service;

import com.bloodDonation.member.entities.Authorities;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//회원조회서비스
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username) //이메일 조회
                .orElseGet(() -> memberRepository.findByUserId(username) //아이디로 조회
                        .orElseThrow(() -> new UsernameNotFoundException(username)));

        //authorities 데이터를 꺼내서 memberinfo쪽에 유지해주면 자동으로 회원 권한을 체크해줌
        List<SimpleGrantedAuthority> authorities = null;
        List<Authorities> tmp = member.getAuthorities(); //디비에서 조회했던 데이터를
        if (tmp != null) { //null이 아닐때
            //리스트형태로 값을 가공해서 넣는다
            authorities = tmp.stream()
                    .map(s -> new SimpleGrantedAuthority(s.getAuthority().name()))
                    .toList();

        }
        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getUserPw())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
