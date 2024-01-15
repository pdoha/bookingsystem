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

    /**
     * 이메일 인증 번호 발급 전송
     * @param email
     * @return
     */
    public boolean sendCode(String email) {

        int authNum = (int) (Math.random() * 99999); //인증번호 랜덤

        //데이터 유지하기위해 세션에 저장
        session.setAttribute("EmailAuthNum", authNum); //인증번호
        session.setAttribute("EmailAuthStart", System.currentTimeMillis()); //현재시간

        EmailMessage emailMessage = new EmailMessage(
                email,
                Utils.getMessage("Email.verification.subject", "commons"), //commons message 연결
                Utils.getMessage("Email.verification.message", "commons"));
        Map<String, Object> tplData = new HashMap<>(); //추가 데이터

        //데이터 추가 put
        tplData.put("authNum", authNum);

        //auth : 템플릿
        return sendService.sendMail(emailMessage, "auth", tplData);

    }

    /**
     * 발급 받은 인증번호화 사용자 입력코드와 일치 여부 체크
     * @param code
     * @return
     */
    public boolean check(int code) {
        //인증번호 유지시켜야하니까 세션에 저장
        Integer authNum = (Integer) session.getAttribute("EmailAuthNum");
        //보낸시간
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
