package com.bloodDonation.commons.email;

import com.bloodDonation.commons.email.service.EmailMessage;

import com.bloodDonation.commons.email.service.EmailSendService;
import com.bloodDonation.commons.email.service.EmailVerifyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmailSendTest {

    //이메일 검증
    private EmailVerifyService emailVerifyService;

    @Autowired
    private EmailSendService emailSendService;

    @Test
    void sendTest(){
        EmailMessage message = new EmailMessage("qkrthdus5147@gmail.com", "이메일테스트중", "내용");
        boolean success = emailSendService.sendMail(message);

        assertTrue(success);
    }

    //전송테스트
    @Test
    @DisplayName("템플릿 형태로 전송 테스트")
    void sendWithTplTest(){
        EmailMessage message = new EmailMessage("qkrthdus5147@gmail.com", "이메일 전송 테스트", " 내용");
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authNum", "123456");
        boolean success = emailSendService.sendMail(message, "auth", tplData);

        assertTrue(success);
    }

    @Test
    @DisplayName("이메일 인증 번호 전송 테스트")
    void emailVerifyTest() {
        boolean result = emailVerifyService.sendCode("yonggyo00@kakao.com");
        assertTrue(result);
    }
}
