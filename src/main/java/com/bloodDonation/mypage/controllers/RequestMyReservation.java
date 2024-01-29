package com.bloodDonation.mypage.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestMyReservation {

    @NotBlank
    private long cCode; //헌혈의 집 코드

    private String center; //센터명

    private String bookDate; //예약 날짜

    @NotBlank
    private String bookType; //헌혈 종류
    private String bookHour; //예약 시간
    private String bookMin; //예약 분

    @NotBlank
    private String[] donorTel; //헌혈자 전화번호

}
