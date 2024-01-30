package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.member.Authority;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ControlValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestControl.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestControl form = (RequestControl)target;

        //권한 가져오기
        List<Authority> authorities = form.getAuthorities();
        //권한이 null 이거나 비어있으면 에러메세지
        if(authorities == null || authorities.isEmpty()){
            errors.rejectValue("authorities", "Required");
        }

    }
}
