package com.bloodDonation.commons.email.controllers;

import com.bloodDonation.commons.email.service.EmailVerifyService;
import com.bloodDonation.commons.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // Json 형태로 객체 데이터를 반환
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class ApiEmailController {

    private final EmailVerifyService verifyService;

    /**
     * 이메일 인증 코드 발급
     *
     * @param email
     * @return
     */
    @GetMapping("/verify")
    public JSONData<Object> sendVerifyEmail(@RequestParam("email") String email) {
        JSONData<Object> data = new JSONData<>();

        boolean result = verifyService.sendCode(email);
        data.setSuccess(result);

        return data;
    }


    /**
     * 발급받은 인증코드와 사용자입력코드의 일치여부 체크
     * @return
     */
    @GetMapping("/auth_check")
    public JSONData<Object> sendVerifyEmail(@RequestParam("authNum") int authNum){
        JSONData<Object> data = new JSONData<>();

        boolean result = verifyService.check(authNum);
        data.setSuccess(result);

        return data;
    }
}
