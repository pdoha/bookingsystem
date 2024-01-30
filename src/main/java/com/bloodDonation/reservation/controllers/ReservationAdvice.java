package com.bloodDonation.reservation.controllers;

import com.bloodDonation.reservation.constants.DonationType;
import com.bloodDonation.reservation.constants.ReservationStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice("com.bloodDonation") //다시 확인해보기
public class ReservationAdvice {
    @ModelAttribute("donationTypes")
    public List<String[]> getDonationTypes() {
        return DonationType.getList();
    }

    @ModelAttribute("reservationStatuses")
    public List<String[]> getStatuses() {
        return ReservationStatus.getList();
    }
}
