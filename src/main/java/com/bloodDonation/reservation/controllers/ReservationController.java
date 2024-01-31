package com.bloodDonation.reservation.controllers;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.calendar.Calendar;
import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.ListData;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.reservation.entities.Reservation;
import com.bloodDonation.reservation.service.ReservationApplyService;
import com.bloodDonation.reservation.service.ReservationDateService;
import com.bloodDonation.reservation.service.SearchCenterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
@SessionAttributes({"requestReservation", "availableTimes"})
public class ReservationController implements ExceptionProcessor {

    private final ReservationApplyService reservationApplyService;
    private final ReservationMainValidator reservationMainValidator;
    private final ReservationDateService reservationDateService;
    private final SearchCenterService searchCenterService;
    private final Calendar calendar;
    private final Utils utils;

    private final HttpServletRequest request;

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
        return new String[] { "reservation/common" };
    }



    /**
     * 센터 필터링 검색/선택
     *
     * @param search
     * @param model
     * @return
     */
    @GetMapping("/centerChoice")
    public String centerChoice(@ModelAttribute("rCenterSearch") RCenterSearch search, Model model) {

        ListData<CenterInfo> data = searchCenterService.getList(search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());
        model.addAttribute("pageTitle", "헌혈의집 선택");

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
        commonProcess("step1", model);

        form.setCCode(cCode);
        form.setMode("step1");
        model.addAttribute("requestReservation", form);

        Map<String, Object> data = calendar.getData(form.getYear(), form.getMonth());
        model.addAllAttributes(data);

        return utils.tpl("reservation/step1");
    }

    @PostMapping("/step2")
    public String step2(RequestReservation form, Errors errors, Model model) {
        commonProcess("step2", model);


        reservationMainValidator.validate(form, errors);

        if(errors.hasErrors()) {
            Map<String, Object> data = calendar.getData(form.getYear(), form.getMonth());
            model.addAllAttributes(data);
            return utils.tpl("reservation/step1");
        }

        form.setMode("step2");

        //검증 성공시 예약 시간대 블록 조회
        List<LocalTime> availableTimes = reservationDateService.getAvailableTimes(form.getCCode(), form.getDate());

        model.addAttribute("availableTimes", availableTimes);

        return utils.tpl("reservation/step2");
    }


    @PostMapping("/apply")
    public String apply(RequestReservation form, Errors errors, Model model) {
        commonProcess("apply", model);

        reservationMainValidator.validate(form,errors);
        System.out.println(form);

        if(errors.hasErrors()) {
            return utils.tpl("reservation/step2");
        }


        String url = request.getContextPath() + "/reservation/check";
        String script = String.format("parent.location.replace('%s');",url);

        model.addAttribute("script", script);

        return "common/_execute_script";
    }
    @GetMapping("/check")
    public String check(RequestReservation form, Model model) {
        commonProcess("check", model);

        return utils.tpl("reservation/userInfo_check");
    }

    @PostMapping("/SucessBooking")
    public String success(RequestReservation form, Errors errors, Model model, SessionStatus status) {
        commonProcess("success", model);

        form.setMode("step3");

        reservationMainValidator.validate(form, errors);

        if (errors.hasErrors()) { // 약관 동의시에만 성공 페이지로 이동
            return utils.tpl("reservation/userInfo_check");
        }

        //예약 신청 처리
        Reservation reservation = reservationApplyService.apply(form);
        model.addAttribute("reservation", reservation);

        status.setComplete(); // 세션 비우기

        return utils.tpl("reservation/success_booking");
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = "헌혈의집 찾기";
        mode = StringUtils.hasText(mode) ? mode : "search";

        List<String> addCss = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        addCss.add("reservation/style");
        addScript.add("reservation/common");


        if (mode.equals("check")) {

        } else if (mode.equals("success")) {

        } else if (mode.equals("search")) {
            addCommonScript.add("area");
        }


        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("pageTitle", pageTitle);
    }
}
