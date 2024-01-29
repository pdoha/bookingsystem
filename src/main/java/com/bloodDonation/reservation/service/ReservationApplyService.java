package com.bloodDonation.reservation.service;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.reservation.constants.DonationType;
import com.bloodDonation.reservation.constants.ReservationStatus;
import com.bloodDonation.reservation.controllers.RequestReservation;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 예약 신청 처리
 */
@Service
@RequiredArgsConstructor
public class ReservationApplyService {

    private final ReservationRepository reservationRepository;
    private final CenterInfoService centerInfoService;
    private final MemberUtil memberUtil;
    public Reservation apply(RequestReservation form) {

        CenterInfo center = centerInfoService.get(form.getCCode());
        LocalDateTime bookDateTime = LocalDateTime.of(form.getDate(), form.getTime());

        Reservation reservation = Reservation.builder()
                .bookDateTime(bookDateTime)
                .bookType(DonationType.valueOf(form.getBookType()))
                .center(center)
                .member(memberUtil.getMember())
                .capacity(form.getPersons())
                .status(ReservationStatus.APPLY)
                .build();

        reservationRepository.saveAndFlush(reservation);

        return reservation;
    }
}
