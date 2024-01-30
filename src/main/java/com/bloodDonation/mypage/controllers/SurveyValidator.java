/*package com.bloodDonation.mypage.controllers;

import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SurveyValidator implements Validator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestSurvey form = (RequestSurvey) target;
        boolean[] questions1 = form.getQuestions1();
        boolean[] questions2= form.getQuestions2();

        //questions1[0]~[4]증에 체크가 안된게 있으면 메세지띄우기-라디오버튼 체크여부검증을 if에 조건에 넣기
        if () {

            errors.rejectValue("confirmPassword", "Mismatch.userPw");
        }

        if (memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            if (!encoder.matches(password, member.getUserPw())) {
                errors.rejectValue("userPw", "Mismatch");
            }
        }
    }
}*/
