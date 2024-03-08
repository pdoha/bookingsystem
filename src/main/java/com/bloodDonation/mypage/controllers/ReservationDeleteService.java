package com.bloodDonation.mypage.controllers;

import com.bloodDonation.commons.exceptions.UnAuthorizedException;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import com.bloodDonation.reservation.service.ReservationInfoService;

public class ReservationDeleteService {
    private ReservationRepository repository;
    private ReservationInfoService infoService;
    private MemberUtil memberUtil;

    public void delete(Long bookCode) {

        Reservation reservation = infoService.get(bookCode);

        if (!memberUtil.isLogin() || !memberUtil.getMember().getUserId().equals(reservation.getMember().getUserId())) {
            throw new UnAuthorizedException();
        }

        repository.delete(reservation);
        repository.flush();

    }
}
