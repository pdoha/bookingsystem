package com.bloodDonation.commons.rests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JSONData<T> {

    private HttpStatus status = HttpStatus.OK;
    private boolean success = true; //성공여부

    @NonNull
    private T data; //성공시 데이터
    private String message;

}
