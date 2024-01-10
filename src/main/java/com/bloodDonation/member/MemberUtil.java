package com.bloodDonation.member;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
//메세지 부분 일괄로 지울 수 있는 메서드
public class MemberUtil {
    //현재 로그인 세션 비우는 것

    public static void clearLoginData(HttpSession session){
        //userId 와 메세지 지우기
        session.removeAttribute("userId");
        session.removeAttribute("NotBlank_userId");
        session.removeAttribute("NotBlank_userPw");
        session.removeAttribute("Global_error");

    }

}
