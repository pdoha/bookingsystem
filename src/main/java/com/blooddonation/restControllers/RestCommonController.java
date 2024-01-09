package com.blooddonation.restControllers;


import com.blooddonation.commons.exceptions.CommonException;
import com.blooddonation.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.swing.plaf.PanelUI;

@RestControllerAdvice("org.blooddonation.restControllers")
public class RestCommonController {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e){
        //공통 응답코드 = 500
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //500
        if(e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false); //실패
        data.setStatus(status);
        data.setMessage(e.getMessage());

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);
    }


}
