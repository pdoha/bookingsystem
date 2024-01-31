package com.bloodDonation.Survey.service;

import com.bloodDonation.Survey.repository.SurveyRepository;
import com.bloodDonation.mypage.entities.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SurveyInfoService {
    private final SurveyRepository repository;

    public Survey get(Long seq) {
        Survey survey = repository.findById(seq).orElseThrow(SurveyNotFoundException::new);

        getResult(survey);

        return survey;
    }

    public boolean getResult(Survey survey) {
        int positive = 0, negative = 0;
        if (survey.isQ1()) positive++;
        else negative++;

        if (survey.isQ2()) positive++;
        else negative++;

        if (survey.isQ3()) positive++;
        else negative++;

        if (survey.isQ4()) positive++;
        else negative++;

        if (survey.isQ5()) positive++;
        else negative++;

        if (survey.isQ6()) positive++;
        else negative++;

        if (survey.isQ7()) positive++;
        else negative++;

        if (survey.isQ8()) positive++;
        else negative++;

        if (survey.isQ9()) positive++;
        else negative++;

        if (survey.getQ10() == null || survey.getQ10().equals("false")) negative++;
        else positive++;

        if (survey.getQ10() == null || survey.getQ10().equals("false")) negative++;
        else positive++;

        if (survey.getQ11() == null || survey.getQ11().equals("false")) negative++;
        else positive++;

        //네 아니요 개수가 아직 체크되지 않았는데 여기서 11개라고 확정해놓음?
        survey.setResult(negative == 11);
        return negative == 11;
    }
}
