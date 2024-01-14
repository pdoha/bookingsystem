package com.bloodDonation.member.service;


import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final MemberRepository memberRepository;

    public Optional<Member> searchMyInfo(String userId) {

        return memberRepository.findByUserId(userId);
    }

    public void modifyMyInfo(Member imember){
        Member member = new Member();
        member.setEmail(imember.getEmail());
        member.setMName(imember.getMName());
        member.setUserId(imember.getUserId());

        //DB에 저장
        memberRepository.saveAndFlush(member);
    }

}
