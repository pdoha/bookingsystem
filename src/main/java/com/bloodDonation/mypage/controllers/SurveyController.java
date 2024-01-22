package com.bloodDonation.mypage.controllers;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.mypage.entities.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class SurveyController {
    private final Utils utils;
    private final Survey survey;
    @PostMapping("/surveyresult")
    public String surveyresult(){
        if(survey.getPositive()==10) {

            return utils.tpl("mypage/surveyresult");
        }
        return impossible();
    }
    @PostMapping("/surveyimpossible")
    public String impossible(){
        if(survey.getNegative() >= 1){
            return utils.tpl("mypage/surveyimpossible");
        }
        return surveyresult();
    }
}
