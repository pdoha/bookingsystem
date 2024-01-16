package com.bloodDonation.member.service;


import com.bloodDonation.member.MemberUtil;
import com.bloodDonation.member.entities.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

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

        //세션에서 정보가져오려면 필요
        MemberInfo memberInfo = (MemberInfo)authentication.getPrincipal();
        Member member = memberInfo.getMember();
        session.setAttribute("member", member);

        String redirectURL = request.getParameter("redirectURL");
        redirectURL = StringUtils.hasText(redirectURL) ? redirectURL : "/";

        response.sendRedirect(request.getContextPath() + redirectURL);

    }
}
