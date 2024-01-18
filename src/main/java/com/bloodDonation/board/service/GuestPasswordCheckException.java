package com.bloodDonation.board.service;


import com.bloodDonation.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 비회원 비밀번호 확인이 필요한 경우
 *
 */
public class GuestPasswordCheckException extends CommonException {
    //비회원일 경우 비밀 번호 확인
    public GuestPasswordCheckException() {
        super(HttpStatus.UNAUTHORIZED);

    }

}
