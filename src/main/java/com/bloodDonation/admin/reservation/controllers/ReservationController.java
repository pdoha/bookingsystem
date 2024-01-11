package com.bloodDonation.admin.reservation.controllers;

import com.bloodDonation.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reservation")
public class ReservationController implements ExceptionProcessor {

    @ModelAttribute("menuCode")
    public String getMenuCode(){
        return "reservation";
    }

    @GetMapping
    public String list(Model model) {
        return "admin/reservation/list";
    }

}
