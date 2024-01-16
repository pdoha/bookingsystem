package com.bloodDonation.commons;

import com.bloodDonation.admin.config.controllers.BasicConfig;
import com.bloodDonation.file.service.FileInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


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


    //가입 이메일과 회원명으로 일치하는 회원이 있다면 비밀번호를 임의의 비밀번호로 초기화
    //알파벳, 숫자, 특수문자 조합 랜덤 문자열 생성
    public String randomChars(){
        return randomChars(8); //비밀번호 8자
    }
    //어떤 데이터가 와도 같은 방식으로 데이터를 처리 Stream 사용
    public String randomChars(int length){

        // int -> char -> String
        //알파벳 생성 대소문자 다 합친 s를 char 타입으로 만들고 string타입으로 변환
        Stream<String> alphas = IntStream.concat(IntStream.rangeClosed((int)'a', (int)'z'), //특정범위의 정수 (a < x <=z)
                                //일반 스트림 → 기본자료형 스트림으로 변환 메소드 : mapToObj 받은 char 문자열을 -> String 객체로
                                IntStream.rangeClosed((int)'A', (int)'Z')).mapToObj(s -> String.valueOf((char)s)); //특정범위의 정수 (A < X < Z)
        //숫자 생성 + string 타입
        Stream<String> nums = IntStream.range(0, 10).mapToObj(String::valueOf);

        //특수문자 생성 + string타입
        Stream<String> specials = Stream.of("~","!","@","#","$","%","^","&","*","(",")","_","+","-","=","[","{","}","]",";",":");
        //위에 만든 알파벳 + 숫자 + 특수문자를 collect로 모아서 toCollection을 이용해서 list형태로 변환해준다
        List<String> chars = Stream.concat(Stream.concat(alphas, nums), specials).collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(chars); //shuffle 섞는다

        return chars.stream().limit(length).collect(Collectors.joining());
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

    /**
     * 비회원 UID(Unique ID)
     *          IP + 브라우저 정보
     *
     * @return
     */
    public int guestUid() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }
}
