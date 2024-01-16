package com.bloodDonation.mypage.controllers;

import lombok.Data;

@Data
/*마이페이지-개인정보변경할 항목이 담긴 커맨드 객체*/
public class RequestMemberInfo {
    private String mName;
    private String userPw;
    private String confirmPw;
    private String zonecode;
    private String address;
    private String addressSub;
}
