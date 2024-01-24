package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.reservation.controllers.RequestReservation;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.reservation.constants.DonationType;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationSaveService {
    private final ReservationRepository reservationRepository;
    private final MemberUtil memberUtil;

    public Reservation save(RequestReservation form) {

        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add_reservation";
        Long bookCode = form.getBookCode();

        Reservation data = null;
        if(mode.equals("edit_reservation") && bookCode != null) {
            data = reservationRepository.findById(bookCode).orElseThrow(ReservationNotFoundException::new);
        } else {
            data = new Reservation();
            //회원정보 가져오기
            data.setMember(memberUtil.getMember());
        }

        String donorTel = Arrays.stream(form.getDonorTel()).collect(Collectors.joining("-"));
        data.setDonorTel(donorTel);
        data.setBookType(DonationType.valueOf(form.getBookType()));
        //예약자 이름 어떻게?
        //센터 ,예약날짜, 회원번호

        reservationRepository.saveAndFlush(data);
        return data;

    }
}
