package com.bloodDonation.commons.email.service;

import com.bloodDonation.commons.Utils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {
    private final EmailSendService sendService;
    private final HttpSession session;

    public boolean sendCode(String email) {
        int authNum = (int) (Math.random() * 99999);

        session.setAttribute("EmailAuthNum", authNum);
        session.setAttribute("EmailAuthStart", System.currentTimeMillis());

        EmailMessage emailMessage = new EmailMessage(
                email,
                Utils.getMessage("Email.verification.subject", "commons"),
                Utils.getMessage("Email.verification.message", "commons"));
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authNum", authNum);

        return sendService.sendMail(emailMessage, "auth", tplData);

    }

    public boolean check(int code) {
        Integer authNum = (Integer) session.getAttribute("EmailAuthNum");
        Long stime = (Long) session.getAttribute("EmailAuthStart");
        if (authNum != null && stime != null) {
            //인증시간 만료 여부 체크 - 3분 유효 시간
            boolean isExpired = (System.currentTimeMillis() - stime.longValue()) > 1000 * 60 * 3;
            if (isExpired) {//만료되었다면 세션 비우고 검증 실패처리
                session.removeAttribute("EmailAuthNum");
                session.removeAttribute("EmailAuthStart");

                return false;
            }

            //사용자 입력 코드와 발급 코드가 일치하는지 여부 체크
            boolean isVerified = code == authNum.intValue();
            session.setAttribute("EmailAuthVerified", isVerified);

            return isVerified;
        }
        return false;
    }
}
