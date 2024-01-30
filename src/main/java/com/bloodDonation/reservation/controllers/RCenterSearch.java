package com.bloodDonation.reservation.controllers;

import lombok.Data;

import java.util.List;

@Data
public class RCenterSearch {
    private Long cCode; // 지점 코드

    private int page = 1;
    private int limit = 20;

    private String sopt;    // 검색 옵션
    private String skey;    // 검색 키워드

    private String address; // 주소
    private String addressSub; // 나머지 주소

    private boolean weekday; //  평일 예약 가능 여부
    private boolean weekend; //  주말 예약 가능 여부
    private boolean bookHday; // 공휴일 예약 가능 여부
    private boolean bookAfter18; // 18시 이후 예약 가능 여부
}
