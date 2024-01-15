package com.bloodDonation.commons;

import com.bloodDonation.admin.config.controllers.BasicConfig;
import com.bloodDonation.file.service.FileInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


@Component
@RequiredArgsConstructor
public class Utils {
    //유효성 검증 관련
    private final HttpServletRequest request;
    private final HttpSession session;
    private final FileInfoService fileInfoService;

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

    /**
     * \n 또는 \r\n -> <br>
     * @param str
     * @return
     */
    public String nl2br(String str) {
        str = Objects.requireNonNullElse(str,"");
        str = str.replaceAll("\\n","<br>")
                .replaceAll("\\r","");

        return str;
    }

    /**
     * 썸네일 이미지 사이즈 설정
     *
     * @return
     */
    public List<int[]> getThumbSize() {
        BasicConfig basicConfig = (BasicConfig)request.getAttribute("siteConfig");
        String thumbSize = basicConfig.getThumbSize(); // \r\n
        String[] thumbsSize = thumbSize.split("\\n");
        List<int[]> data = Arrays.stream(thumbsSize)
                .filter(StringUtils::hasText)
                .map(s -> s.replaceAll("\\s+",""))//공백제거
                .map(this::toConvert).toList();

        return data;
    }

    private int[] toConvert(String size) {
        size = size.trim();

        int[] data = Arrays.stream(size.replaceAll("\\r","").toUpperCase().split("X")).mapToInt(Integer::parseInt).toArray();

        return data;
    }

    public String printThumb(long seq, int width, int height, String className) {//주로 타임리프내에서 사용

        String[] data = fileInfoService.getThumb(seq,width,height);
        if(data != null) {
            String cls = StringUtils.hasText(className) ? "class='" + className + "'":"";
            String image = String.format("<img src='%s'%s>",data[1], cls);

            return image;
        }

        return "";

    }

    public String printThumb(long seq, int width, int height) {
        return printThumb(seq, width, height, null);
    }

    /**
     * 0이하 정수 인 경우 1이상 정수로 대체
     *
     * @param num
     * @param replace
     * @return
     */
    public static int onlyPositiveNumber(int num, int replace) {
        return num < 1 ? replace : num;
    }

    /**
     * 요청 데이터 단일 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String getParam(String name) {
        return request.getParameter(name);
    }

    /**
     * 요청 데이터 복수개 조회 편의 함수
     *
     * @param name
     * @return
     */
    public String[] getParams(String name) {
        return request.getParameterValues(name);
    }
}
