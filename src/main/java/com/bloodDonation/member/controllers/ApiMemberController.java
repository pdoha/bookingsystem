package com.bloodDonation.member.controllers;

import com.bloodDonation.commons.ExceptionRestProcessor;
import com.bloodDonation.commons.rests.JSONData;
import com.bloodDonation.member.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //json 형태로 반환
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class ApiMemberController implements ExceptionRestProcessor {

    private final MemberRepository memberRepository;

    //이메일 중복 여부 체크
    @GetMapping("/email_dup_check")
    public JSONData<Object> duplicateEmailCheck(@RequestParam("email") String email){

        boolean isExists = memberRepository.existsByEmail(email);

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(isExists);

        return data;
    }
}
