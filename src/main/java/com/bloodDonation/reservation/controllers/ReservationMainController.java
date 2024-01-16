package com.bloodDonation.reservation.controllers;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationMainController {
    private final ReservationRepository reservationRepository;
    private final Utils utils;

    @GetMapping("/main")
    public String mainPage() {
        return utils.tpl("reservation/main");
    }
}
