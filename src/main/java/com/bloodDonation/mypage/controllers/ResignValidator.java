package com.bloodDonation.mypage.controllers;

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
public class ResignValidator implements Validator {

    private final MemberUtil memberUtil;
    private final PasswordEncoder encoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestUnRegister form = (RequestUnRegister) target;
        String password = form.getUserPw();
        String confirmPassword = form.getConfirmPassword();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPw", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotBlank");

        if (StringUtils.hasText(password) && StringUtils.hasText(confirmPassword)
                && !password.equals(confirmPassword)) {

            errors.rejectValue("confirmPassword", "Mismatch.userPw");
        }

        if (memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            if (!encoder.matches(password, member.getUserPw())) {
                errors.rejectValue("userPw", "Mismatch");
            }
        }
    }
}
