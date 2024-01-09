package com.blooddonation.controllers;

import com.blooddonation.commons.exceptions.CommonException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.blooddonation.controllers")
public class CommonController {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, HttpServletResponse response, Model model){


        //모든 예외는 500으로 고정
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //500

        //e가 CommonException의 객체인지 체크
        if(e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }
        return "error/common";
    }
}
