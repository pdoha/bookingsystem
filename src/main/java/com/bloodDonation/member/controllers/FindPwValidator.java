package com.bloodDonation.member.controllers;

import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//비밀번호 찾기 ( 이메일 + 회원명 조합으로 조회 검증)

/**
 * 비밀번호 찾기 추가 검증처리
 */
@Component
@RequiredArgsConstructor
public class FindPwValidator implements Validator {
    private  final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestFindPw.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        //이메일 + 회원명 조합으로 조회되는지 체크
        RequestFindPw form = (RequestFindPw) target;
        String email = form.email();
        String name = form.mName();
        //아이디와 && 회원명 값이 있고 && 아이디 + 회원명으로 조회되지않으면 에러메세지
        if(StringUtils.hasText(email) && StringUtils.hasText(name) && !memberRepository.existsByEmailAndName(email, name)){
            errors.reject("NotFound.member");
        }

    }
}
