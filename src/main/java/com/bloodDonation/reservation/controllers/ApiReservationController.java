package com.bloodDonation.reservation.controllers;

import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.rests.JSONData;
import com.bloodDonation.reservation.service.ReservationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ApiReservationController implements ExceptionProcessor {

    private final ReservationInfoService reservationInfoService;

    @GetMapping("/available_capacity")
    public JSONData<Integer> availableCapacity(
            @RequestParam("cCode") Long cCode,
            @RequestParam("date")LocalDate date,
            @RequestParam("time") LocalTime time) {

        Integer capacity = reservationInfoService.getAvailableCapacity(cCode, LocalDateTime.of(date, time));

        return new JSONData<>(capacity);
    }

}
