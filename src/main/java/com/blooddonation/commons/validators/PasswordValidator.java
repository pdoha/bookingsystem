package com.blooddonation.commons.validators;

public interface PasswordValidator {
    //비밀번호 복잡성 체크 ( 공통 검증 기능)
    //false : 대문자 1개 이상, 소문자 1개 이상 포함
    //true : 대소문자 구분없이 1개 이상 포함

    //알파벳이 대소문자가 들어가 있는지
    //2번째 매개변수에 boolean 값
    default boolean alphaCheck(String password, boolean caseIncensitive){
        //2가지 형태로 구분해서 패턴을 만든다
        if(caseIncensitive){
            //대소문자 구분없이 체크 (정규표현식 - 0개이상의 문자 1개)
            String pattern = ".*[a-zA-Z]+.*";

            return password.matches(pattern);

        }else{
            //대문자 1개 소문자 1개 포함
            String pattern1 = ".*[a-z]+.*";
            String pattern2 = ".*[A-Z]+.*";

            return password.matches(pattern1) && password.matches(pattern2);

        }

    }

    //숫자가 반드시 포함
    default boolean numberCheck(String password){
        //앞뒤 문자 포함되고 d = Digital 중간에 숫자가 포함되어있는
        return password.matches(".*\\d+.*");

    }

    //특수문자 포함 여부
    default boolean specialCharsCheck(String password){
        //문자하나하나를 의미하는데  [ ] 안에는 이중에서 한개만 있으면됨
        String pattern = ".*[`~!@#$%^*&()-_+=]+.*";

        return password.matches(pattern);

    }
}
