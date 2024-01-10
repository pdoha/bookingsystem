package com.blooddonation.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ResourceBundle;


@Component
@RequiredArgsConstructor
public class Utils {
    //유효성 검증 관련
    private final HttpServletRequest request;
    private final HttpSession session;

    //메세지 코드에 관한 번들 가져오기
    private static final ResourceBundle commonsBundle;
    private static final ResourceBundle validationBundle;
    private static final ResourceBundle errorsBundle;

    //상황에 따라 맞는 다른 메세지를 가져올 수 있다
    //static 객체를 만들지 않아도 클래스가 로드될때 실행된다
    static {
        commonsBundle = ResourceBundle.getBundle("messages.commons");
        validationBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }




    public boolean isMobile(){
        //모바일 수동 전환 모드 체크
        String device = (String) session.getAttribute("device");
        if(StringUtils.hasText(device)){
            return device.equals("MOBILE");
        }


        //요청헤더 : User-Agent 패턴을 가지고 모바일인지 아닌지 체크
        //getHeader를 통해가져온다
        String ua = request.getHeader("User-Agent");

        //패턴
        String pattern = ".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*";

        //위에 패턴이 있으면 모바일로 인지

        return  ua.matches(pattern);
    }


    public String tpl(String path){
        String prefix = isMobile() ? "mobile/" : "front/";

        return prefix + path;
    }

    public static String getMessage(String code, String type){
        //값이 존재하면 타입 그대로 넣고 없으면 validations 검증
        type = StringUtils.hasText(type) ? type : "validations";

        //기본값은 고정하고 입력하면 입력한대로 메세지 찾아서
        ResourceBundle bundle = null;

        if (type.equals("commons")){
            bundle = commonsBundle;
        }else if (type.equals("errors")){
            bundle = errorsBundle;
        }else {
            bundle = validationBundle;
        }


        return bundle.getString(code);
    }

    //유효성 검사 메세지 가져오기
    public static String getMessage(String code){
        return getMessage(code, null);
    }

}
