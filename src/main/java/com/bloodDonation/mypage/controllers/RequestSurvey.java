package com.bloodDonation.mypage.controllers;

import lombok.Data;

@Data
public class RequestSurvey {
     private String mode = "step1";
     private  boolean[] questions1;
     private boolean[] questions2;
     private String[] questions3;
     private String[] details1;
     private String[] details2;
     private String[] details3;
     //question1,2는 페이지
     //각 페이지의 질문-라디오버튼 이 name값이 boolean[]
}
