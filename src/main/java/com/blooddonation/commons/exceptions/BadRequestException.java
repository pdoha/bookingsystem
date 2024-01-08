package com.blooddonation.commons.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CommonException{

    public BadRequestException(String message){
        //응답코드 400으로 고정
        super(message, HttpStatus.BAD_REQUEST);
    }
}
