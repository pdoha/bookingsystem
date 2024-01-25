package com.bloodDonation.mypage.service.survey;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.mypage.entities.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@RequestMapping("/result")
public class SurveyInfoService {
    private final Utils utils;
    private Survey survey;

    public String result(Survey survey){

        int positive=survey.getPositive();
        int negative = survey.getNegative();

        if(positive==11){
            return utils.tpl("survey/result");
        }else{
            return utils.tpl("survey/surveyimpossible");
        }
    }
}
