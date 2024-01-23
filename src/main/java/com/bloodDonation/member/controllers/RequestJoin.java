package com.bloodDonation.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;


//검증 받을 데이터
@Data
public class RequestJoin {

    private String gid = UUID.randomUUID().toString();

    @NotBlank @Email
    private String email;

    @NotBlank
    private String userId;

    @NotBlank
    private String userPw;

    @NotBlank
    private String mName;

    @NotBlank
    private String confirmPassword;

    private String ecode;
    //전화번호추가?
    //private String[] mobile;

    //주소
    private String zonecode; //우편번호
    private String address; //주소
    private String addressSub; //상세주소


    @AssertTrue
    private boolean agree;


}
