package com.bloodDonation.mypage.controllers;


import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController implements ExceptionProcessor {

    private final Utils utils;
    private final MyPageService service;

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
    public String modifiy(Model model) {
        commonProcess("modify", model);

        return utils.tpl("mypage/modify");
    }

    @PostMapping("/modify222")
    public String modifiy22(@RequestBody Member member) {
        service.modifyMyInfo(member);
        return utils.tpl("mypage/modify");
    }

    @PostMapping("/modify")
    public String modifyPs(Model model) {
        commonProcess("modify", model);

        return "redirect:/mypage/info";
    }
    @GetMapping("/reservation")
    public String reservation(Model model){

        return utils.tpl("mypage/reservation");
    }

     @GetMapping("/reservation/modify")
            public String reservationModify(Model model){

            return utils.tpl("mypage/reservation/modify");
     }

    @PostMapping("/survey")
    public String survey(Model model){

        return utils.tpl("mypage/survey");
    }
    @GetMapping("/bloodview")
    public String bloodview(Model model){

        return utils.tpl("mypage/bloodview");
    }
    @GetMapping("/surveyresult")
    public String surveyResult(Model model){

        return utils.tpl("mypage/surveyresult");
    }
    @GetMapping("/unregister")
    public String unregister(Model model){

        return utils.tpl("mypage/unregister");
    }

    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "main");
        String pageTitle = Utils.getMessage("마이페이지", "commons");

        if (mode.equals("info")) {
            pageTitle = Utils.getMessage("개인정보_조회", "commons");
        } else if (mode.equals("modify")) {
            pageTitle = Utils.getMessage("개인정보_변경", "commons");
        } else if (mode.equals("reservation")) {
            pageTitle = Utils.getMessage("예약조회", "commons");
        } else if (mode.equals("reservation/modify")) {
            pageTitle = Utils.getMessage("예약변경", "commons");
        } else if (mode.equals("survey")) {
                pageTitle = Utils.getMessage("전자문진", "commons");
        } else if (mode.equals("bloodview")) {
            pageTitle = Utils.getMessage("나의_헌혈내역", "commons");
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
