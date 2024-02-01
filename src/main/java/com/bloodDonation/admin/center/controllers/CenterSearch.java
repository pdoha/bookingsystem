package com.bloodDonation.admin.center.controllers;

import lombok.Data;

import java.util.List;

@Data
public class CenterSearch {
    private int page = 1;
    private int limit = 20;

    private String sopt;    // 검색 옵션
    private String skey;    // 검색 키워드

    private List<Long> cCode; // 지점 코드

    private String sido;
    private String sigugun;
}
