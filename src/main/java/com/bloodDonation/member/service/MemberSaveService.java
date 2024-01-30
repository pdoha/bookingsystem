package com.bloodDonation.member.service;

import com.bloodDonation.admin.member.controllers.RequestControl;
import com.bloodDonation.member.entities.Authorities;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.AuthoritiesRepository;
import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberSaveService {
    //멤버 & 권한 정보 가져오기
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;

    public void updateControl(RequestControl form){

        //회원 정보 하나 가져와서
        Member member = memberRepository.findByUserId(form.getUserId())
                //없을때는 회원없다고 에러 처리
                .orElseThrow(MemberNotFoundException::new);

        member.setEnable(form.isEnable());

        //탈퇴일시
        if(form.isEnable()){
            //회원인 상태
            member.setResignAt(null);
        } else{
            //탈퇴
            member.setResignAt(LocalDateTime.now());
        }
        memberRepository.flush();
        //권한은 가져와서 삭제 후에 다시 추가
        List<Authorities> authorities = member.getAuthorities();
        authoritiesRepository.deleteAll(authorities);
        authoritiesRepository.flush();

        //authorities 하나 더 만들어서
        //넘어 오는건 이넘상수 a가 넘어와서 반환해서 리스트형태로 넘겨준다
        authorities = form.getAuthorities().stream()
                .map(a -> Authorities.builder()
                        .authority(a)
                        .member(member).build()).toList();
        //저장
        //권한 여러개 -> saveAllAndFlush
        authoritiesRepository.saveAllAndFlush(authorities);
    }


}
