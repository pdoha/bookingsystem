package com.bloodDonation.Survey.service;

import com.bloodDonation.Survey.repository.SurveyRepository;
import com.bloodDonation.mypage.entities.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SurveyInfoService {
    private final SurveyRepository repository;
    //검사후에, 이건 나의 검사결과를 불러올 때 findById(seq)로 디비에서 찾아오는 것-필요할 때 구현
    public Survey get(Long seq) {
        Survey survey = repository.findById(seq).orElseThrow(SurveyNotFoundException::new);

        getResult(survey);

        return survey;
    }

    //전자문진 완료 후에 결과페이지 나올때 쓰이는 로직-디비에 저장한 것을 통해, 개수를 세어 바로 결과페이지로 연결
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

        if (survey.getQ11() == null || survey.getQ11().equals("false")) negative++;
        else positive++;


        survey.setPositive(positive);
        survey.setNegative(negative);

        survey.setResult(negative == 11);
        return negative == 11;
    }
}
