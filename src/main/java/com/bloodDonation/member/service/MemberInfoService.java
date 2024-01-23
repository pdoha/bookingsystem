package com.bloodDonation.member.service;

import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getUserPw())
                .member(member)
                .build();
    }
}
