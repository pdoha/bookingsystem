package com.bloodDonation.mypage.controllers;

import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import com.bloodDonation.reservation.constants.DonationType;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationModifyService {
    private final ReservationRepository reservationRepository;
    private final CenterInfoService centerInfoService;
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;

    public Reservation modify(RequestMyReservation form) {
        if (!memberUtil.isLogin()) {
            return null;
        }

        Member member = memberUtil.getMember();
        member = memberRepository.findByUserId(member.getUserId()).orElse(null);
        //---로그인한 회원에서 예약정보를 가져오려면 member엔티티에 reservation을 추가해야 됨??

        Reservation reservation = new Reservation();
        reservation.setCenter(form.getCenter());
        
        reservation.setBookDateTime(LocalDateTime.of(form.getDate(), form.getTime()));
        reservation.setDonorTel(form.getDonorTel());
        reservation.setBookType(DonationType.valueOf(form.getBookType()));
        reservation.setCapacity(form.getPersons());

       // LocalDateTime bookDateTime = LocalDateTime.of(form.getDate(), form.getTime());
        reservationRepository.saveAndFlush(reservation);

        return reservation;
    }
}


