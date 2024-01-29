package com.bloodDonation.commons.interceptors;

import com.bloodDonation.admin.config.controllers.BasicConfig;
import com.bloodDonation.admin.config.service.ConfigInfoService;
import com.bloodDonation.member.MemberUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final ConfigInfoService infoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        checkDevice(request); //기기 체크
        clearLoginData(request);
        loadSiteConfig(request);

        return true;
    }

    /**
     * PC, 모바일 수동 변경 처리
     *
     *  // device - PC : PC 뷰, Mobile : Mobile 뷰
     * @param request
     */
    private void checkDevice(HttpServletRequest request) {
        String device = request.getParameter("device");
        if (!StringUtils.hasText(device)) {
            return;
        }

        device = device.toUpperCase().equals("MOBILE") ? "MOBILE" : "PC";

        HttpSession session = request.getSession();
        session.setAttribute("device", device);
    }

    private void clearLoginData(HttpServletRequest request) {
        String URL = request.getRequestURI();
        if (URL.indexOf("/member/login") == -1) { //문자열이 포함되지 않을때
            HttpSession session = request.getSession(); //세션 객체 가져와서
            MemberUtil.clearLoginData(session);  //만들어놓은  MemberUtil 이용
        }
    }

    /**
     * 24.01.11
     * 허용되는 파일 확장자
     * @param request
     */
    private void loadSiteConfig(HttpServletRequest request) {
        String[] excludes = {".js", ".css", ".png", ".jpg", ".jpeg", "gif", ".pdf", ".xls", ".xlxs", ".ppt"};

        String URL = request.getRequestURI().toLowerCase();//대문자,소문자 구분 없이 소문자로 반환하기

        boolean isIncluded = Arrays.stream(excludes).anyMatch(s -> URL.contains(s));//허용되는 확장자인지 아닌지 학인
        if (isIncluded) {
            return;
        }

        BasicConfig config = infoService.get("basic", BasicConfig.class)
                .orElseGet(BasicConfig::new);

        request.setAttribute("siteConfig", config);
    }
}
