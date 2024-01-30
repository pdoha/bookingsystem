package com.bloodDonation.mypage.controllers;

import com.bloodDonation.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SurveyValidator implements Validator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestSurvey.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestSurvey form = (RequestSurvey) target;
        String mode = form.getMode();

        //questions1[0]~[4]증에 체크가 안된게 있으면 메세지띄우기-라디오버튼 체크여부검증을 if에 조건에 넣기


    }

    private void validateStep1(RequestSurvey form, Errors errors) {
        boolean[] questions1 = form.getQuestions1();
        if (questions1 == null || questions1.length < 5) {
            errors.reject("Required.question", "설문 문항을 모두" +
                    "선택하세요");
        }
    }

    private void validateStep2(RequestSurvey form, Errors errors) {
        boolean[] questions2 = form.getQuestions2();
        String[] questions3 = form.getQuestions3();

        if (questions2 == null || questions2.length < 4) {
            errors.reject("Required.question", "설문 문항을 모두" +
                    "선택하세요");
        }
        if (questions3 == null || questions3.length < 2) {
            errors.reject("Required.question", "설문 문항을 모두" +
                    "선택하세요");
        }
    }
}