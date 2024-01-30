package com.bloodDonation.mypage.service.survey;

import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.SurveyRepository;
import com.bloodDonation.mypage.controllers.RequestSurvey;
import com.bloodDonation.mypage.entities.Survey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyApplyService {
    private final SurveyRepository surveyRepository;
    private final MemberUtil memberUtil;

    public void apply(RequestSurvey form) {
        Member member = memberUtil.getMember();//로그인 후 메인으로 이동한 다음 마이페이지에 로그인 회원정보있어야 함
        ObjectMapper om = new ObjectMapper();

        int positive = 0, negative = 0;

        String data = null;
        try {
            data = om.writeValueAsString(form);

        } catch (JsonProcessingException e) {}

        boolean [] questions1=form.getQuestions1();
        boolean [] questions2=form.getQuestions2();


        for(int i=0; i<5; i++){
            if(questions1[i]==true){
                positive++;
            }else{
                negative++;
            }
        }

        for(int i=5; i<11; i++){
            if(questions2[i]==true){
                positive++;
            }else{
                negative++;
            }
        }

        /*System.out.println("네------"+positive);
        System.out.println("아니요------"+negative);*/
        /*List<Survey> surveys = member.getSurveys();*/
        /*Survey survey = (Survey) surveys;*/
       Survey survey = Survey.builder()
                .data(data)
                .positive(positive)
                .negative(negative)
                .member(memberUtil.getMember())//로그인 후 메인으로 이동한 다음 마이페이지에 로그인 회원정보있어야 함
                .build();
       /* System.out.println("survey 후"+survey.getPositive());
        System.out.println("survey 후"+survey.getNegative());*/
        surveyRepository.saveAndFlush(survey);

    }
}
