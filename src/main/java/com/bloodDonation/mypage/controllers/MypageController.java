package com.bloodDonation.mypage.controllers;


import com.bloodDonation.commons.ExceptionProcessor;
import com.bloodDonation.commons.Utils;
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

    @PostMapping("/modify")
    public String modifyPs(Model model) {
        commonProcess("modify", model);

        return "redirect:/mypage/info";
    }
    @PostMapping("/survey")
    public String survey(Model model){

        return utils.tpl("mypage/survey");
    }
    @GetMapping("/bloodview")
    public String bloodview(Model model){

        return utils.tpl("mypage/bloodview");
    }
    @GetMapping("/survey")
    public String surveyResult(Model model){

        return utils.tpl("mypage/surveyresult");
    }

    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "main");
        String pageTitle = Utils.getMessage("마이페이지", "commons");

        if (mode.equals("info")) {
            pageTitle = Utils.getMessage("개인정보_조회", "commons");
        } else if (mode.equals("modify")) {
            pageTitle = Utils.getMessage("개인정보_변경", "commons");
        }

        model.addAttribute("pageTitle", pageTitle);
    }

}
