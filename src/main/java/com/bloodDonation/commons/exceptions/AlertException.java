package com.bloodDonation.commons.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 24.01.09
 * 매개 변수가 message, status인 경우 exception처리
 * NotFoundException에 필요함.
 */
public class AlertException extends CommonException {
    public AlertException(String message, HttpStatus status) {
        super (message,status);
    }

}
