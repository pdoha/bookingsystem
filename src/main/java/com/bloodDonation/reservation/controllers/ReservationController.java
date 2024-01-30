package com.bloodDonation.reservation.controllers;

import com.bloodDonation.admin.center.controllers.CenterSearch;
import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.admin.center.service.CenterInfoService;
import com.bloodDonation.calendar.Calendar;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.reservation.service.ReservationApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
@SessionAttributes("requestReservation")
public class ReservationController implements ExceptionProcessor {

    private final ReservationApplyService reservationApplyService;
    private final ReservationMainValidator reservationMainValidator;
    private final CenterInfoService centerInfoService;
    private final Calendar calendar;
    private final Utils utils;

    @ModelAttribute("requestReservation")
    public RequestReservation requestReservation() {
        return  new RequestReservation();
    }

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[] { "reservation/style" };

    }

    @ModelAttribute("addScript")
    public String[] addScript() {
        return new String[] { "reservation/reservation" };
    }



    /**
     * 센터 필터링 검색/선택
     *
     * @param search
     * @param model
     * @return
     */
    @GetMapping("/centerChoice")
    public String centerChoice(@ModelAttribute CenterSearch search, Model model) {

        ListData<CenterInfo> data = centerInfoService.getList(search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("reservation/centerChoice");
    }



    /**
     * 예약날짜 선택 (캘린더 노출 )
     * @param cCode
     * @param form
     * @param model
     * @return
     */
    @GetMapping("/step1/{cCode}")
    public String step1(@PathVariable("cCode") Long cCode, @ModelAttribute RequestReservation form, Model model) {
        form.setCCode(cCode);
        form.setMode("step1");
        model.addAttribute("requestReservation", form);

        Map<String, Object> data = calendar.getData(form.getYear(), form.getMonth());
        model.addAllAttributes(data);

        return utils.tpl("reservation/step1");
    }

    @PostMapping("/step2")
    public String step2(RequestReservation form, Errors errors, Model model) {
        reservationMainValidator.validate(form, errors);

        if(errors.hasErrors()) {
            Map<String, Object> data = calendar.getData(form.getYear(), form.getMonth());
            model.addAllAttributes(data);
            return utils.tpl("reservation/step1");
        }

        form.setMode("step2");

        return utils.tpl("reservation/step2");
    }


    @PostMapping("/apply")
    public String apply(RequestReservation form, Errors errors, Model model, SessionStatus status) {

        reservationMainValidator.validate(form,errors);
        System.out.println(form);

        if(errors.hasErrors()) {
            return utils.tpl("reservation/step2");
        }

        //예약 신청 처리
        reservationApplyService.apply(form);

        status.setComplete(); //세션 비우기
        return "common/_execute_script";
    }

}
