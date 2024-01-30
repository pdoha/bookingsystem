package com.bloodDonation.admin.member.controllers;

import com.bloodDonation.commons.validators.PasswordValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UpdateValidator implements Validator, PasswordValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestUpdate.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestUpdate form = (RequestUpdate) target;
        String userPw = form.getUserPw();
        String confirmPw = form.getConfirmPw();

        //있을때만 검증 (비밀번호 입력했을때만 검증)
        if(StringUtils.hasText(userPw)){
            if(userPw.length() < 8){
                errors.rejectValue("userPw", "Size");
            }
            if(!alphaCheck(userPw, false) || !numberCheck(userPw) || !specialCharsCheck(userPw)){
                errors.rejectValue("userPw", "Complexity");
            }
            if(StringUtils.hasText(confirmPw) && !userPw.equals(confirmPw)){
                errors.rejectValue("confirmPw", "Mismatch");
            }
        }


    }
}
