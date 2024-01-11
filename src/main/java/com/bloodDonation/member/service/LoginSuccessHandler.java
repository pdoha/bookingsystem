package com.bloodDonation.member.service;


import com.bloodDonation.member.MemberUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


//로그인 성공
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
     HttpServletResponse response, Authentication authentication) throws IOException,
     ServletException {
        HttpSession session = request.getSession();
        //session 데이터 지우기
        MemberUtil.clearLoginData(session);

    }
}
