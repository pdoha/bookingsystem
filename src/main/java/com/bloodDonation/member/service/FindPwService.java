package com.bloodDonation.member.service;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.commons.email.service.EmailMessage;
import com.bloodDonation.commons.email.service.EmailSendService;
import com.bloodDonation.member.controllers.FindPwValidator;
import com.bloodDonation.member.controllers.RequestFindPw;
import com.bloodDonation.member.entities.Member;
import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.Map;


//비밀번호 초기화 처리 및 메일 전송 서비스

/**
 * 비밀번호 찾기 양식 검증 및 초기화 메일 전송
 *
 */
@Service
@RequiredArgsConstructor
public class FindPwService {

    private final FindPwValidator validator;
    private final MemberRepository memberRepository;
    private final EmailSendService sendService;
    private final PasswordEncoder encoder;
    private final Utils utils;

    public void process(RequestFindPw form, Errors errors){
        //비밀번호 찾기 검증
        validator.validate(form, errors);

        if(errors.hasErrors()) {//유효성 검사 실패시 처리 중단
            return;
        }

        //비밀번호 초기화
        reset(form.email());
    }

    public void reset(String email){
        //비밀번호 초기화
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        String newPassword = utils.randomChars(12); //초기화된 비밀번호는 12자로 생성
        member.setUserPw(encoder.encode(newPassword));

        memberRepository.saveAndFlush(member);

        EmailMessage emailMessage = new EmailMessage(email, Utils.getMessage("Email.password.reset", "commons"),
                                                            Utils.getMessage("Email.password.reset", "commons"));
        Map<String, Object> tplData = new HashMap<>(); //추가 데이터
        tplData.put("userPw", newPassword);
        sendService.sendMail(emailMessage, "password_reset", tplData); //비밀번호초기화 메세지, 추가데이터 (새로운비밀번호)
    }
}
