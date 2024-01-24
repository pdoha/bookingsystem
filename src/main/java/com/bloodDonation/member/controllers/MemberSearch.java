package com.bloodDonation.member.controllers;

import lombok.Data;

//멤버 조회하는 페이징 커맨드 객체
@Data
public class MemberSearch {
    private int page = 1;
    private int limit = 20;

    private String sopt;
    private String skey;
}
