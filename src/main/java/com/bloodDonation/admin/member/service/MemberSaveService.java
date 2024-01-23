package com.bloodDonation.admin.member.service;

import com.bloodDonation.member.controllers.RequestJoin;
import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;

    public void save(RequestJoin form){

    }
}
