package com.bloodDonation.board.controllers.comment;

import com.bloodDonation.commons.validators.PasswordValidator;
import com.bloodDonation.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CommentFormValidator implements Validator, PasswordValidator {
//공통은 commend객체 바뀌는 부분(비회원 같은)은 Validator

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {

        return clazz.isAssignableFrom(RequestComment.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(memberUtil.isLogin()) {//로그인상태일때는 비회원 비밀번호 체크 X
            return;
        }

        RequestComment form = (RequestComment) target;

        String questPw = form.getGuestPw();
        if(!StringUtils.hasText(questPw)) {
            errors.rejectValue("guestPw","NotBlank");
        }

        if (StringUtils.hasText(questPw)) {

            if(questPw.length() < 6) {
                errors.rejectValue("guestPw", "Size");
            }

            if (!alphaCheck(questPw, true) || !numberCheck(questPw)) {
                errors.rejectValue("guestPw", "Complexity");
            }
        }//endif
    }
}
