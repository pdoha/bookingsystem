package com.blooddonation.commons;

import com.blooddonation.commons.exceptions.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ExceptionProcessor {

    @ExceptionHandler(Exception.class)
    default String errorHandler(Exception e, HttpServletResponse response, HttpServletRequest request, Model model){


        //모든 예외는 500으로 고정
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //500

        //e가 CommonException의 객체인지 체크
        if(e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        response.setStatus(status.value());
        //콘솔에 에러출력
        e.printStackTrace();

        model.addAttribute("status",status.value());
        model.addAttribute("path", request.getRequestURL());
        model.addAttribute("method", request.getMethod());
        model.addAttribute("message", e.getMessage());

        return "error/common";
    }
}
