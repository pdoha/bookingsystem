package com.bloodDonation.member.controllers;

import com.bloodDonation.commons.validators.PasswordValidator;
import com.bloodDonation.member.repositories.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, PasswordValidator {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public boolean supports(Class<?> clazz) {
        //검증대상을 한정
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         * 1. 이메일, 아이디 중복 여부 체크
         * 2. 비밀번호 복합성 체크 - 대소문자 1개 각각 포함, 숫자 1개이상 포함, 특수문자도 1개 이상 포함
         * 3. 비밀번호, 비밀번호 확인 일치 여부 체크
         */

        //필요한 것 - 이메일, 아이디, 등 form에서 가져오기
        RequestJoin form = (RequestJoin) target;
        String email = form.getEmail();
        String userId = form.getUserId();
        String userPw = form.getUserPw();
        String confirmPassword = form.getConfirmPassword();

        // 이메일 중복 여부 체크 ( 별도 메서드 추가해서 하자)
        if (StringUtils.hasText(email) && memberRepository.existsByEmail(email)){
            //이메일 값이 있고 이메일이 존재하면 에러메세지
            errors.rejectValue("email", "Duplicated");
        }

        //이메일 인증 했는지 체크
        //NotVerified.email=이메일 확인이 필요합니다.
        Boolean EmailAuthVerified = (Boolean) session.getAttribute("EmailAuthVerified");
        //EmailAuthVerified = true; //이메일 비활성화 true 값
        if (EmailAuthVerified == null || !EmailAuthVerified) {
            errors.rejectValue("email", "NotVerified");
        }

        // 아이디 중복 여부 체크
        if (StringUtils.hasText(userId) && memberRepository.existsByUserId(userId)){
            //아이디 값이 있고 아이디가 존재하면 에러메세지
            errors.rejectValue("userId", "Duplicated");
        }

        //3. 비밀번호, 비밀번호 확인 일치 여부 체크
        if (StringUtils.hasText(userPw) && StringUtils.hasText(confirmPassword)
            && !userPw.equals(confirmPassword)) {
            //비밀번호가 존재하고 && 비밀번호확인이 존재하고 && 둘이 일치하지않을때
            errors.rejectValue("confirmPassword", "Mismatch.password");
        }


        //2. 비밀번호 복잡성 체크 - 대소문자 1개 각각 포함, 숫자1개 이상 포함, 특수문자 1개 이상 포함
        if(StringUtils.hasText(userPw) &&
                (!alphaCheck(userPw, true) || !numberCheck(userPw)
                || !specialCharsCheck(userPw))){
            errors.rejectValue("userPw", "Complexity");
        }

    }
}
