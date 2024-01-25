package com.bloodDonation.mypage.service.survey;

import com.bloodDonation.member.MemberUtil;
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

        ObjectMapper om = new ObjectMapper();

        int positive = 0, negative = 0;

        String data = null;
        try {
            data = om.writeValueAsString(form);

        } catch (JsonProcessingException e) {}

        boolean [] questions1=form.getQuestions1();
        boolean [] questions2=form.getQuestions2();

        for (boolean result : questions1) {
           if (result) positive++;
           else negative++;

        }

        for (boolean result : questions2) {
            if (result) positive++;
            else negative++;
        }

        System.out.println("네------"+positive);
        System.out.println("아니요------"+negative);

        Survey survey = Survey.builder()
                .data(data)
                .positive(positive)
                .negative(negative)
                .member(memberUtil.getMember())
                .build();
        System.out.println("survey에 담기는지-------"+survey.getPositive());
        System.out.println("survey에 담기는지-------"+survey.getNegative());
        surveyRepository.saveAndFlush(survey);

    }
}
