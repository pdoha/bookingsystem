package com.bloodDonation.member.service;

import com.bloodDonation.commons.Utils;
import com.bloodDonation.member.MemberUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    //로그인 실패
    @Override
    //매개변수 ( 요청 / 응답 / 예외 객체)
    public void onAuthenticationFailure(HttpServletRequest request,
     HttpServletResponse response, AuthenticationException exception) throws
     IOException, ServletException {

        HttpSession session = request.getSession();

        //세션 로그인 실패 메세지 일괄 삭제
        MemberUtil.clearLoginData(session);

        String username = request.getParameter("userId");
        String password = request.getParameter("userPw");

        session.setAttribute("userId", username);

        //validations 메세지를 이용하자
        //username, password가 없는 경우 메세지 출력
        if(!StringUtils.hasText(username)){
            session.setAttribute("NotBlank_userId", Utils.getMessage("NotBlank.userId"));
        }
        if(!StringUtils.hasText(password)){
            session.setAttribute("NotBlank_userPw", Utils.getMessage("NotBlank.userPw"));
        }

        // username, password 전부 있는 경우 - 아이디 또는 비밀번호가 틀릴때
        if(StringUtils.hasText(username) && StringUtils.hasText(password)){
            session.setAttribute("Global_error", Utils.getMessage("Fail.login", "errors"));
        }
        //탈퇴-메세지(로그인 실패)
        if(exception instanceof DisabledException){
            session.setAttribute("Global_error",
                    Utils.getMessage("Resign.member", "errors"));
        }

        //로그인 실패하면 로그인페이지로 이동하고 원인을 알려준다
        response.sendRedirect(request.getContextPath() + "/member/login");

        //-> redirect로 이동하면 데이터 유지가 안되서 session에 담아서 메세지 출력
        //session이 request 보다 큰 범위

    }
}
