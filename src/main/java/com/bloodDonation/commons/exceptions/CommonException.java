package com.bloodDonation.commons.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonException extends RuntimeException{

    //모든 예외는 응답코드 가지고 조회할거야
    private HttpStatus status;


    //기본 생성자
    public CommonException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    /**
     * 24.01.18
     * 공통 exception의 status 받아와서 retun(반환)함.
     * @param status : 매개변수 1개일때
     */
    public CommonException(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }

}
