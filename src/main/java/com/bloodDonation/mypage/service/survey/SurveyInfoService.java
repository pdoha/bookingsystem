package com.bloodDonation.mypage.service.survey;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.SurveyRepository;
import com.bloodDonation.mypage.entities.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SurveyInfoService {
    private final Utils utils;
    private final SurveyRepository surveyRepository;
    private final MemberUtil memberUtil;
    private Survey survey;

    public String result(){
        Member member = memberUtil.getMember();
        List<Survey> surveys = member.getSurveys();

        int positive=0;
        int negative=0;
        if(surveys.isEmpty()) {
            Survey survey1 = (Survey) surveys;

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
