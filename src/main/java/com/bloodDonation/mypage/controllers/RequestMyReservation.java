package com.bloodDonation.mypage.controllers;

import com.bloodDonation.admin.center.entities.CenterInfo;
import com.bloodDonation.member.entities.Member;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RequestMyReservation {

    @NotBlank
    private long cCode; //헌혈의 집 코드

    private CenterInfo center; //센터명

    /*private String bookDate; //예약 날짜

    @NotBlank
    private String bookType; //헌혈 종류
    private String bookHour; //예약 시간
    private String bookMin; //예약 분*/
    @NonNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date; //예약 일자

    @NonNull
    @DateTimeFormat(pattern="HH:mm")
    private LocalTime time; // 예약 시간

    @NotBlank
    private String bookType;

    private Member member;


    @Min(1)
    private int persons; //예약 인원 수

    @NotBlank
    private String donorTel; //헌혈자 전화번호

}
