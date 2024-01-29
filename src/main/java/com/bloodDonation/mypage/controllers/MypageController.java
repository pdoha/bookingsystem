package com.bloodDonation.mypage.controllers;


import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.mypage.service.MemberDeleteService;
import com.bloodDonation.mypage.service.MemberUpdateService;
import com.bloodDonation.mypage.service.MyPageModifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController implements ExceptionProcessor {

    private final Utils utils;
    private final MyPageModifyService service;
    private final MemberUtil memberUtil;
    private final MemberUpdateService updateService;
    private final MemberDeleteService deleteService;
    @ModelAttribute("addCss")
    public String[] getAddCss() {

        return new String[] { "mypage/style" };
    }

    @ModelAttribute("addScript")
    public String[] getAddScript() {

        return new String[] { "mypage/common"};
    }

    /**
     * 마이페이지 첫 화면
     *
     * @param model
     * @return
     */
    @GetMapping
    public String index(Model model) {
        commonProcess("main", model);

        return utils.tpl("mypage/index");
    }

    @GetMapping("/info")
    public String info(Model model) {
        commonProcess("info", model);


        return utils.tpl("mypage/info");
    }

    @GetMapping("/modify")
    //RequestMemberInfo로 변경
    public String modify(@ModelAttribute RequestMemberInfo form, Model model) {
        commonProcess("modify", model);
    //로그인 되었을때
        if (memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            form = new ModelMapper().map(member, RequestMemberInfo.class);
        }

        model.addAttribute("requestMemberInfo", form);

        return utils.tpl("mypage/modify");
    }

    @PostMapping("/modify")
    public String modifyPs(@Valid RequestMemberInfo form, Model model) {
        commonProcess("modify", model);

        updateService.update(form);

        return "redirect:/mypage/info";
    }
    @GetMapping("/reservation")
    public String reservation(Model model){
        commonProcess("reservation", model);
        return utils.tpl("mypage/reservation");
    }

     @GetMapping("/reservation/modify")
     public String reservationModify(@ModelAttribute RequestMyReservation form, Model model){
            commonProcess("reservation/modify",model);

         if (memberUtil.isLogin()) {
             Member member = memberUtil.getMember();//예약은 getReservation() 이용?
             form = new ModelMapper().map(member, RequestMyReservation.class);
         }

         model.addAttribute("requestMyReservation", form);
            return utils.tpl("mypage/reservation_modify");
     }

    @PostMapping("/reservation/modify")
    public String reservationModifyPs(@Valid RequestMyReservation form, Model model) {
        commonProcess("modify", model);

        //예약변경 데이터를 저장해주는 서비스-form을 담아

        return "redirect:/mypage/reservation";
    }

    @GetMapping("/survey") //전자문진 안내 페이지-나머지 전자문진은 surveyController쪽에 있음
    public String survey(Model model){
        commonProcess("survey", model);
        return utils.tpl("mypage/survey");
    }

    @GetMapping("/bloodview")
    public String bloodview(Model model){
        commonProcess("bloodview", model);
        return utils.tpl("mypage/bloodview");
    }
    @GetMapping("/myprint")
    public String myprint(Model model){
        //commonProcess("myprint", model);
        return utils.tpl("mypage/myprint");
    }

    @GetMapping("/unregister")
    public String unregister(@ModelAttribute RequestUnRegister form, Model model){
        commonProcess("unregister", model);
        return utils.tpl("mypage/unregister");
    }

    @PostMapping("/unregister")
    public String unreggisterPs(@Valid RequestUnRegister form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return utils.tpl("mypage/unregister");
        }
        deleteService.delete(form);
        return "redirect:/";//탈퇴 후 메인페이지로
    }

    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "main");
        String pageTitle = Utils.getMessage("마이페이지", "commons");

        List<String> addCommonScript = new ArrayList<>();

        if (mode.equals("info")) {
            pageTitle = Utils.getMessage("개인정보_조회", "commons");
        } else if (mode.equals("modify")) {
            pageTitle = Utils.getMessage("개인정보_변경", "commons");
            addCommonScript.add("address");

        } else if (mode.equals("reservation")) {
            pageTitle = Utils.getMessage("예약조회", "commons");
        } else if (mode.equals("reservation/modify")) {
            pageTitle = Utils.getMessage("예약변경", "commons");


        } else if (mode.equals("survey")) {
                pageTitle = Utils.getMessage("전자문진안내", "commons");

        } else if (mode.equals("bloodview")) {
            pageTitle = Utils.getMessage("나의_헌혈내역", "commons");
        } else if (mode.equals("myprint")) {
            pageTitle = Utils.getMessage("헌혈증서출력", "commons");
        } else if (mode.equals("dosurvey")) {
            pageTitle = Utils.getMessage("전자문진", "commons");
        } else if (mode.equals("surveyresult")) {
            pageTitle = Utils.getMessage("검사결과", "commons");

        } else {
            if(mode.equals("unregister")) {
                pageTitle = Utils.getMessage("회원탈퇴", "commons");
            }
        }


            model.addAttribute("pageTitle", pageTitle);
    }

}
