package com.blooddonation.commons.rests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JSONData<T> {

    private HttpStatus status = HttpStatus.OK;
    private boolean success = true; //성공여부
    private T data; //성공시 데이터
    private String message;

}
