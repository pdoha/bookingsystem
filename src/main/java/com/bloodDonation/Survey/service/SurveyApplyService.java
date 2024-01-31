package com.bloodDonation.Survey.service;

import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.Survey.repository.SurveyRepository;
import com.bloodDonation.Survey.controllers.RequestSurvey;
import com.bloodDonation.mypage.entities.Survey;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyApplyService {
    private final SurveyRepository repository;
    private final SurveyInfoService infoService;
    private final MemberUtil memberUtil;

    public boolean apply(RequestSurvey form) {

        Survey survey = new ModelMapper().map(form, Survey.class);
        survey.setMember(memberUtil.getMember());

        repository.saveAndFlush(survey);

        return infoService.getResult(survey);
    }
}
