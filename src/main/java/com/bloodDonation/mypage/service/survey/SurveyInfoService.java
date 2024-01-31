package com.bloodDonation.mypage.service.survey;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.MemberUtil;
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
    private final MemberUtil memberUtil;
    private Survey survey;

    public String result(){
        /*Member member = memberUtil.getMember();*///로그인 후 메인으로 이동한 다음 마이페이지에 로그인 회원정보있어야 함
        /*List<Survey> surveys = member.getSurveys();*/
        Optional<Survey> survey = surveyRepository.findBymemberUserNo(memberUtil.getMember().getUserNo());
        int positive=0;
        int negative=0;
        /*if(!surveys.isEmpty()) {
            Survey survey1 = (Survey) surveys;*/
        if(survey.isPresent()){
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
