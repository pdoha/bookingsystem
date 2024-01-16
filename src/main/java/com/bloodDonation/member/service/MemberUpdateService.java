package com.bloodDonation.member.service;


import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.repositories.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final MemberUtil memberUtil;
    private final HttpSession session;
    public void update(HttpSession session) {

    }
}
