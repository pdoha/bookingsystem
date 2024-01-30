package com.bloodDonation.reservation.controllers;

import com.bloodDonation.admin.center.controllers.CenterSearch;
import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
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
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ApiReservationController implements ExceptionProcessor {

    private final ReservationInfoService reservationInfoService;
    private final CenterInfoService centerInfoService;

    @GetMapping("/available_capacity")
    public JSONData<Integer> availableCapacity(
            @RequestParam("cCode") Long cCode,
            @RequestParam("date") LocalDate date,
            @RequestParam("time") LocalTime time) {

        Integer capacity = reservationInfoService.getAvailableCapacity(cCode, LocalDateTime.of(date, time));

        return new JSONData<>(capacity);
    }

    @GetMapping("/center")
    public JSONData<List<CenterInfo>> getCenters(@RequestParam(name = "skey", required = false) String skey) {

        CenterSearch search = new CenterSearch();
        search.setSopt("cName");
        search.setSkey(skey);
        search.setLimit(10000);

        ListData<CenterInfo> data = centerInfoService.getList(search);

        return new JSONData<>(data.getItems());
    }
}