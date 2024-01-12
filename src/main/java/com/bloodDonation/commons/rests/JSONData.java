package com.bloodDonation.commons.rests;

import lombok.*;
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
