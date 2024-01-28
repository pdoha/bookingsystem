package com.bloodDonation.mypage.service.survey;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.SurveyRepository;
import com.bloodDonation.mypage.entities.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SurveyInfoService {
    private final Utils utils;
    private final SurveyRepository surveyRepository;
    private Member member;
    private Survey survey;

    public String result(){

        Optional<Survey> survey = surveyRepository.findById(1L);
        /*Optional<Survey> survey = surveyRepository.findByuserNo(member.getUserNo());*/
        int positive=0;
        int negative=0;
        if(survey.isPresent()) {
            Survey survey1 = survey.get();

            positive=survey1.getPositive();
            negative = survey1.getNegative();

        }

        if(negative==11){
            return utils.tpl("survey/result");
        }else{
            return utils.tpl("survey/surveyimpossible");
        }
    }
}
