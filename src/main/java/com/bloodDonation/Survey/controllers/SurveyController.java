package com.bloodDonation.Survey.controllers;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.Survey.service.SurveyApplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/survey")
@RequiredArgsConstructor

public class SurveyController {

    private final Utils utils;
    private final SurveyApplyService applyService;

    // 전자문진 신청 페이지
    @GetMapping
    public String index(@ModelAttribute RequestSurvey form, Model model) {
        commonProcess("question", model);

        return utils.tpl("survey/question");
    }

    // 전자 문진 신청 처리
    @PostMapping
    public String apply(@Valid RequestSurvey form, Errors errors, Model model) {

        boolean result = applyService.apply(form);
        String url = "survey/" + (result ? "success" : "failure");

        return utils.tpl(url);
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = Utils.getMessage("전자문진", "commons");
        mode = StringUtils.hasText(mode) ? mode : "question";


        model.addAttribute("pageTitle", pageTitle);
    }


}
