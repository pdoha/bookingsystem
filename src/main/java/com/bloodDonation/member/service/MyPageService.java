package com.bloodDonation.member.service;


import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    //회원정보변경페이지에서 아이디로찾은 회원정보 리턴
    public Optional<Member> searchMyInfo(String userId) {

        return memberRepository.findByUserId(userId);
    }
    //회원정보변경한 정보를 저장
    public void modifyMyInfo(Member imember){
        Member member = new Member();
        member.setEmail(imember.getEmail());
        member.setMName(imember.getMName());
        member.setUserId(imember.getUserId());

        //DB에 저장
        memberRepository.saveAndFlush(member);
    }
    //예약조회페이지에서 변경버튼 눌렀을 때
    public Optional<Reservation> searchMyReservation(String userId){
        return reservationRepository.findByUserId(userId);
    }

    public void modifyMyReservation(Reservation ireservation){
        Reservation reservation = new Reservation();
        reservation.setMember(ireservation.getMember());
        reservation.setBookType(ireservation.getBookType());

        reservationRepository.saveAndFlush(reservation);

    }

}
