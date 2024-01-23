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

        for (boolean result : form.getQuestions1()) {
           if (result) positive++;
           else negative++;
        }

        for (boolean result : form.getQuestions2()) {
            if (result) positive++;
            else negative++;
        }

        Survey survey = Survey.builder()
                .data(data)
                .positive(positive)
                .negative(negative)
                .member(memberUtil.getMember())
                .build();

        surveyRepository.saveAndFlush(survey);
    }
}
