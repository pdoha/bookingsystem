package com.bloodDonation.reservation.controllers;

import com.bloodDonation.admin.center.controllers.CenterSearch;
import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.admin.reservation.controllers.RequestReservation;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationMainController {

    private final CenterInfoService centerInfoService;
    private final Utils utils;

    @GetMapping("/main")
    public String mainPage() {
        return utils.tpl("reservation/main");
    }

    @GetMapping("/centerChoice")
    public String centerChoice(@ModelAttribute CenterSearch search, Model model) {

        ListData<CenterInfo> data = centerInfoService.getList(search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("reservation/centerChoice");}
}
