package com.blooddonation.commons.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        checkDevice(request);
        return true;
    }

    /**
     * pc, mobile 수동 변경처리 ( 쿼리스트링 이용)
     * @param request
     */
    private void checkDevice(HttpServletRequest request){ //session 가지고 등록

        //파라미터 값 가져오기
        String device = request.getParameter("device");

        //값이 없을 경우
        if(!StringUtils.hasText(device)){
            return;
        }

        //값이 있을 경우
        //대소문자 구분 없음
        //MOBILE 이 아니면 PC
        device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";

        //SESSION 가져와서 넣자
        HttpSession session = request.getSession();

        //값이 있으면 고정
        session.setAttribute("device" , device);
    }
}
